package com.shire42.api.loan.client.async.impl;

import com.shire42.api.loan.client.async.EventService;
import com.shire42.api.loan.client.async.dto.LoanContractDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EventServiceImpl implements EventService {

    @Value(value = "${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, LoanContractDTO> kafkaTemplate;

    @Override
    @Async
    public void sendEvent(LoanContractDTO dto) {
        log.info("Send event to topic {}", topicName);
        this.kafkaTemplate.send(topicName, dto);
    }
}
