package com.shire42.api.bank.config;

import com.shire42.api.bank.domain.port.in.AccountDepositUseCase;
import com.shire42.api.bank.domain.port.in.AccountSearchUseCase;
import com.shire42.api.bank.domain.port.in.AccountWithdrawalUseCase;
import com.shire42.api.bank.domain.port.out.AccountDepositRepository;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.port.out.AccountWithdrawalRepository;
import com.shire42.api.bank.domain.service.AccountDepositService;
import com.shire42.api.bank.domain.service.AccountSearchService;
import com.shire42.api.bank.domain.service.AccountWithdrawalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public AccountDepositUseCase accountDepositUseCase(
            final AccountDepositRepository accountDepositRepository,
            final AccountSearchRepository accountSearchRepository) {
        return new AccountDepositService(accountDepositRepository, accountSearchRepository);
    }

    @Bean
    public AccountSearchUseCase accountSearchUseCase(final AccountSearchRepository accountSearchRepository) {
        return new AccountSearchService(accountSearchRepository);
    }

    @Bean
    public AccountWithdrawalUseCase accountWithdrawalUseCase(final AccountWithdrawalRepository accountWithdrawalRepository, final AccountSearchRepository accountSearchRepository) {
        return new AccountWithdrawalService(accountWithdrawalRepository, accountSearchRepository);
    }

}
