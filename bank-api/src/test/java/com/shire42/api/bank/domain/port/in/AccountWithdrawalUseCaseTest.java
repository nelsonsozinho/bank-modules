package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.port.out.AccountWithdrawalRepository;
import com.shire42.api.bank.domain.service.AccountWithdrawalService;
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
public class AccountWithdrawalUseCaseTest {

    @Mock
    private AccountWithdrawalRepository accountWithdrawalRepository;

    @Mock
    private AccountSearchRepository accountSearchRepository;

    private AccountWithdrawalService accountWithdrawalService;

    @BeforeEach()
    void setup() {
        this.accountWithdrawalService = new AccountWithdrawalService(accountWithdrawalRepository, accountSearchRepository);
    }

    @Test
    void shouldWithdrawalUseCase() {
        Account account = Account.builder()
                .id(1L)
                .number("1234567890")
                .balance(BigDecimal.valueOf(1000).doubleValue())
                .build();

        when(accountSearchRepository.findAccountByNumber(account.getNumber())).thenReturn(account);

        accountWithdrawalService.makeWithdrawal(account.getId().toString(), account.getNumber(), BigDecimal.valueOf(100));

        verify(accountSearchRepository).findAccountByNumber(account.getNumber());

        assertEquals(account.getBalance(), BigDecimal.valueOf(900).doubleValue());
    }

}
