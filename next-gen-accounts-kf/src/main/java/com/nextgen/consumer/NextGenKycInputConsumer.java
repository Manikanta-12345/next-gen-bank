package com.nextgen.consumer;

import com.nextgen.constants.KafkaTopics;
import com.nextgen.dto.NextGenKycEvent;
import com.nextgen.service.KycProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class NextGenKycInputConsumer {

    @Autowired
    private KycProcessorService kycProcessorService;

    public NextGenKycInputConsumer(KycProcessorService kycProcessorService) {
        this.kycProcessorService = kycProcessorService;
    }

    @KafkaListener(
            topics = "next.gen.kyc.input.request",
            groupId = "next-gen-kyc-input-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleKycInputEvent(@Payload NextGenKycEvent kycUploadEvent,
                                    @Header(KafkaHeaders.RECEIVED_KEY) String customerId) {

        log.info("Received KYC input event for customer {}: {}", customerId, kycUploadEvent);
        kycProcessorService.processKycInputEvent(kycUploadEvent, customerId);

    }
}
