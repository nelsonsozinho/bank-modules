package com.shire42.api.bank.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shire42.api.bank.adapter.in.dto.LoanContractDTO;
import com.shire42.api.bank.domain.model.LoanContractEvent;
import com.shire42.api.bank.domain.port.in.LoanContractConsumerUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanKafkaListener {

    private final LoanContractConsumerUseCase loanContractConsumerUseCase;

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(
            topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Async
    public void listener(ConsumerRecord<String, String> record) throws JsonProcessingException {
        final LoanContractDTO dto = mapper.readValue(record.value(), LoanContractDTO.class);
        LoanContractEvent event = LoanContractEvent.builder()
                .clientId(dto.getClientId())
                .bancAccountNumber(dto.getBancAccountNumber())
                .loanValue(dto.getLoanValue())
                .build();
        loanContractConsumerUseCase.process(event);
    }

}
