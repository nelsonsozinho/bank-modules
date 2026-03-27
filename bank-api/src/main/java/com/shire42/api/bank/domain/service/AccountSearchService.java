package com.shire42.api.bank.domain.service;

import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.in.AccountSearchUseCase;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;

public class AccountSearchService implements AccountSearchUseCase {

    private AccountSearchRepository accountSearchRepository;

    public AccountSearchService(final AccountSearchRepository accountSearchRepository) {
        this.accountSearchRepository = accountSearchRepository;
    }

    @Override
    public Account findAccountByNumber(String number) {
        return accountSearchRepository.findAccountByNumber(number);
    }

    @Override
    public Account findAccountById(Long id) {
        return accountSearchRepository.findAccountById(id);
    }
}
