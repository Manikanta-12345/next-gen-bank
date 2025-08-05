package com.nextgen.service;

import com.nextgen.dto.NextGenKycEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NextGenKycService {

    private KafkaTemplate<Object, Object> kafkaTemplate;

    NextGenKycService(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendNextGenKycEvent(NextGenKycEvent nextGenKycEvent) {

        //enhancement please upload these documents to s3 and account service will read from s3 and validate kyc and create account

        kafkaTemplate.send("next.gen.kyc.input.request",nextGenKycEvent.getCustomerId(), nextGenKycEvent)
                .whenComplete((nextGenKycEvent1, throwable) -> {
            if (throwable != null) {
                log.error("Failed to send NextGenKycEvent: {}", nextGenKycEvent, throwable);
                throw new RuntimeException("Exception while sending NextGenKycEvent");
            }else  {
                log.info("Send NextGenKycEvent Successfull: {}", nextGenKycEvent1.getRecordMetadata());
            }
        });

        return "Kyc details uploaded successfully";
    }
}
