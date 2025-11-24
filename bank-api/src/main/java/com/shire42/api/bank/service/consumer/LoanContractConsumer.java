
package com.shire42.api.bank.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shire42.api.bank.service.AccountService;
import com.shire42.api.bank.service.consumer.dto.LoanContractDTO;
import com.shire42.api.bank.service.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Log4j2
@RequiredArgsConstructor
public class LoanContractConsumer {

    private final ObjectMapper mapper = new ObjectMapper();

    private final AccountService accountService;

    @KafkaListener(
            topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Async
    public void listenLoanTopic(ConsumerRecord<String, String> record) throws JsonProcessingException {
        LoanContractDTO dto = mapper.readValue(record.value(), LoanContractDTO.class);
        accountService.makeDeposit(
                dto.getClientId().toString(),
                dto.getBancAccountNumber(),
                BigDecimal.valueOf(dto.getLoanValue()),
                TransactionType.LOAN);
    }

}
