package com.nextgen.service;

import com.nextgen.constant.AccountType;
import com.nextgen.constants.KafkaTopics;
import com.nextgen.dto.NextGenKycEvent;
import com.nextgen.entity.CustomerAccount;
import com.nextgen.exception.InvalidAadharException;
import com.nextgen.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class KycProcessorService {

    private KafkaTemplate<Object, Object> kafkaTemplate;
    private AccountRepository customerAccountRepository;

    public KycProcessorService(KafkaTemplate<Object, Object> kafkaTemplate, AccountRepository customerAccountRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerAccountRepository = customerAccountRepository;
    }
    @Transactional
    public void processKycInputEvent(NextGenKycEvent event, String customerId) {
        //read from s3 and for every event insert into audit table
        //retry and send to DLQ if any failure and insert into table for analysis

        if (event.getAadharNumber() == null || event.getPanNumber() == null) {
            log.warn("Invalid KYC data for customer {}: {}", customerId, event);
            return;
        }
        //retry and dead letter scenario
        if (event.getAadharNumber().startsWith("A")) {
            log.error("======== Invalid aadhar number for customer ======== {}: {}", customerId, event);
            throw new InvalidAadharException("Invalid aadhar number");
        }


        String status = isValidForApproval(event) ? "APPROVED" : "REJECTED";
        event.setKycStatus(status);

        kafkaTemplate.send(KafkaTopics.KYC_OUTPUT_TOPIC, customerId, event).whenComplete((result, ex) -> {
            if (ex != null) {
                //store in DB and cron job to send to topic
                log.error("Failed to send KYC output event for customer {}: {}", customerId, ex.getMessage(), ex);
            } else {
                log.info("Sent {} KYC event for customer {}", status, customerId);
                saveCustomerRecord(event, customerId, status);
            }
        });
    }

    private boolean isValidForApproval(NextGenKycEvent event) {
        return "PENDING".equalsIgnoreCase(event.getKycStatus()) && event.getAadharNumber() != null && event.getAadharNumber().length() >= 12;
    }

    private void saveCustomerRecord(NextGenKycEvent event, String customerId, String status) {
        try {
            CustomerAccount account = null;
            account = customerAccountRepository.findByCustomerId(customerId);
            if (Objects.nonNull(account)) {
                account.setId(account.getId());
            } else {
                account = new CustomerAccount();
            }
            account.setCustomerId(customerId);
            account.setAadharNumber(event.getAadharNumber());
            account.setPanNumber(event.getPanNumber());
            account.setKycStatus(status);
            account.setAccountType(AccountType.SAVINGS);
            account.setIsActive("Y");
            account.setCreatedDate(LocalDateTime.now());
            customerAccountRepository.save(account);
            log.info("Customer record saved for customerId {}", customerId);
        }catch (Exception e){
            log.error("Failed to save customer record for customerId {}", customerId, e);
        }
    }
}
