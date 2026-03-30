package com.shire42.api.bank.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shire42.api.bank.adapter.in.kafka.dto.LoanContractDTO;
import com.shire42.api.bank.domain.model.LoanContractEvent;
import com.shire42.api.bank.domain.port.in.LoanContractConsumerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class LoanKafkaListener {

    private final LoanContractConsumerUseCase loanContractConsumerUseCase;

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(
            topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listener(ConsumerRecord<String, String> record) {
        try {
            log.info("Message consumed from partition {} with offset {}: {}", record.partition(), record.offset(), record.value());
            final LoanContractDTO dto = mapper.readValue(record.value(), LoanContractDTO.class);
            log.info("Successfully deserialized loan contract DTO: {}", dto);
            LoanContractEvent event = LoanContractEvent.builder()
                    .clientId(dto.getClientId())
                    .bancAccountNumber(dto.getBancAccountNumber())
                    .loanValue(dto.getLoanValue())
                    .build();
            loanContractConsumerUseCase.process(event);
            log.info("Successfully processed loan contract event");
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize loan contract message: {}", record.value(), e);
        } catch (Exception e) {
            log.error("Error processing loan contract event", e);
        }
    }

}
