package com.shire42.api.bank.service;

import com.shire42.api.bank.client.BankClient;
import com.shire42.api.bank.client.dto.Client;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.repository.AccountRepository;
import com.shire42.api.bank.domain.repository.TransactionRepository;
import com.shire42.api.bank.domain.service.impl.AccountServiceImpl;
import com.shire42.api.bank.domain.service.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankClient bankClient;


    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void shouldMageADepositWithSuccess() {
        String clientId = "123";
        String accountNumber = "123";
        BigDecimal value = new BigDecimal("100.00");

        when(accountRepository.findByNumber(accountNumber)).thenReturn(accountAnswer());
        when(accountRepository.save(any())).thenReturn(accountAnswer());

        accountService.makeWithdrawal(clientId, accountNumber, value);

        verify(accountRepository).save(any());
    }

    @Test
    public void shouldMakeSimpleDeposit() {
        String clientId = "123";
        String accountNumber = "123";
        BigDecimal value = new BigDecimal("100.00");

        when(accountRepository.findByNumber(accountNumber)).thenReturn(accountAnswer());

        accountService.makeDeposit(clientId, accountNumber, value, TransactionType.DEPOSIT);

        verify(accountRepository).findByNumber(any());
        verify(transactionRepository).save(any());
    }

    @Test
    public void shouldMakeSimpleTransaction() {
        String sourceAccountNumber = "123";
        String targetAccountNumber = "321";
        BigDecimal value = new BigDecimal("100.00");
        Client client1 = Client.builder().build();
        Client client2 = Client.builder().build();

        Account sourceAccount = accountAnswer();
        Account targetAccount = accountAnswer();

        when(accountRepository.findByNumber(sourceAccountNumber)).thenReturn(sourceAccount);
        when(accountRepository.findByNumber(targetAccountNumber)).thenReturn(targetAccount);
        when(bankClient.getClientById(any())).thenReturn(client1);
        when(bankClient.getClientById(any())).thenReturn(client2);

        accountService.makeTransaction(sourceAccountNumber, targetAccountNumber, value);

        verify(transactionRepository).save(any());
    }

    private Account accountAnswer() {
        Account account = new Account();
        account.setBalance(Double.parseDouble("400.90"));
        account.setId(1L);
        account.setNumber("123");
        return account;
    }

}
