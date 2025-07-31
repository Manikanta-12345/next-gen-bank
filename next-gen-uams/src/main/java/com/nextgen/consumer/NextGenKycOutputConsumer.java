package com.nextgen.consumer;

import com.nextgen.dto.KycStatus;
import com.nextgen.dto.NextGenKycEvent;
import com.nextgen.entity.UserRegistration;
import com.nextgen.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class NextGenKycOutputConsumer {

       private UserRegistrationRepository userRegistrationRepository;

       NextGenKycOutputConsumer(UserRegistrationRepository userRegistrationRepository) {
           this.userRegistrationRepository = userRegistrationRepository;
       }


        @KafkaListener(topics = "next.gen.kyc.output.response")
        public void handleKycOutputEvent(@Payload NextGenKycEvent kycUploadOutputEvent, @Header(KafkaHeaders.RECEIVED_KEY) String customerId) {
           log.info("kyc verification completed for customer: {}  and event: {}",customerId, kycUploadOutputEvent);
            UserRegistration userRegestration  = userRegistrationRepository.findById(kycUploadOutputEvent.getCustomerId()).get();
            if(Objects.nonNull(userRegestration)) {
                if(kycUploadOutputEvent.getKycStatus().equals("APPROVED")){
                    userRegestration.setKycStatus(KycStatus.APPROVED);
                }else{
                    userRegestration.setKycStatus(KycStatus.REJECTED);
                }
                userRegistrationRepository.save(userRegestration);
            }
        }

}
