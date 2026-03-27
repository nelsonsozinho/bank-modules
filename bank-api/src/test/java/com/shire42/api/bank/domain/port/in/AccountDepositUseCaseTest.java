package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountDepositRepository;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.service.AccountDepositService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountDepositUseCaseTest {

    @Mock
    private AccountDepositRepository accountDepositRepository;

    @Mock
    private AccountSearchRepository accountSearchRepository;

    private AccountDepositService accountDepositService;

    @BeforeEach()
    void setup() {
        this.accountDepositService = new AccountDepositService(accountDepositRepository, accountSearchRepository);
    }

    @Test
    void shouldMakeDepositSuccessfully() {
        Account account = Account.builder()
                .id(1L)
                .number("1234567890")
                .balance(BigDecimal.valueOf(1000).doubleValue())
                .build();

        when(accountSearchRepository.findAccountByNumber("1234567890")).thenReturn(account);

        accountDepositService.makeDeposit("1", "1234567890", BigDecimal.valueOf(100), TransactionType.WITHDRAWAL);

        assertEquals(new BigDecimal(1100).doubleValue(), account.getBalance());
        verify(accountSearchRepository).findAccountByNumber(account.getNumber());
    }

}
