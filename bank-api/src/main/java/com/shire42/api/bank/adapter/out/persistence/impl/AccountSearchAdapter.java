package com.shire42.api.bank.adapter.out.persistence.impl;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import com.shire42.api.bank.adapter.out.persistence.repository.AccountRepository;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountSearchAdapter implements AccountSearchRepository {

     private final AccountRepository accountRepository;

     @Override
     public Account findAccountByNumber(String number) {
          AccountEntity account = accountRepository.findByNumber(number);
          return Account.builder()
                  .id(account.getId())
                  .number(account.getNumber())
                  .balance(account.getBalance())
                  .build();
     }

     @Override
     public Account findAccountById(Long id) {
          AccountEntity account = accountRepository.findById(id).orElse(null);
          return Account.builder()
                  .id(account.getId())
                  .number(account.getNumber())
                  .balance(account.getBalance())
                  .build();
     }
}
