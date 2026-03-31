package com.shire42.api.bank.config;

import com.shire42.api.bank.domain.port.in.AccountDepositUseCase;
import com.shire42.api.bank.domain.port.in.LoanContractConsumerUseCase;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.service.LoanContractConsumerUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConsumerConfig {

    @Bean
    public LoanContractConsumerUseCase loanContractConsumerUseCase(
            final AccountDepositUseCase accountDepositUseCase,
            final AccountSearchRepository accountSearchRepository) {
        return new LoanContractConsumerUseCaseService(accountDepositUseCase, accountSearchRepository);
    }

}
