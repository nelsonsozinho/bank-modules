package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.domain.exceptions.BankAccountsNotFoundException;
import com.shire42.api.bank.domain.exceptions.InsuficientFoundsException;
import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.port.out.AccountTransferRepository;
import com.shire42.api.bank.domain.service.AccountTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionUseCaseTest {

    @Mock
    private AccountTransferRepository accountTransferRepository;

    @Mock
    private AccountSearchRepository accountSearchRepository;

    private AccountTransferService accountTransferService;

    @BeforeEach
    void setup() {
        this.accountTransferService = new AccountTransferService(accountTransferRepository, accountSearchRepository);
    }

    @Test
    void shouldTransferAmountBetweenAccountsSuccessfully() {
        Account sourceAccount = accountWithBalance("10001", 1000d);
        Account targetAccount = accountWithBalance("20002", 300d);
        BigDecimal amount = BigDecimal.valueOf(150);

        when(accountSearchRepository.findAccountByNumber("10001")).thenReturn(sourceAccount);
        when(accountSearchRepository.findAccountByNumber("20002")).thenReturn(targetAccount);

        accountTransferService.transfer("10001", "20002", amount);

        assertEquals(BigDecimal.valueOf(850).doubleValue(), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(450).doubleValue(), targetAccount.getBalance());
        verify(accountTransferRepository).makeTransaction(sourceAccount, targetAccount, amount);
    }

    @Test
    void shouldThrowWhenSourceAccountDoesNotExist() {
        Account targetAccount = accountWithBalance("20002", 300d);

        when(accountSearchRepository.findAccountByNumber("10001")).thenReturn(null);
        when(accountSearchRepository.findAccountByNumber("20002")).thenReturn(targetAccount);

        assertThrows(BankAccountsNotFoundException.class,
                () -> accountTransferService.transfer("10001", "20002", BigDecimal.TEN));

        verify(accountTransferRepository, never()).makeTransaction(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void shouldThrowWhenTargetAccountDoesNotExist() {
        Account sourceAccount = accountWithBalance("10001", 300d);

        when(accountSearchRepository.findAccountByNumber("10001")).thenReturn(sourceAccount);
        when(accountSearchRepository.findAccountByNumber("20002")).thenReturn(null);

        assertThrows(BankAccountsNotFoundException.class,
                () -> accountTransferService.transfer("10001", "20002", BigDecimal.TEN));

        verify(accountTransferRepository, never()).makeTransaction(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void shouldThrowWhenSourceAccountHasInsufficientFunds() {
        Account sourceAccount = accountWithBalance("10001", 50d);
        Account targetAccount = accountWithBalance("20002", 300d);

        when(accountSearchRepository.findAccountByNumber("10001")).thenReturn(sourceAccount);
        when(accountSearchRepository.findAccountByNumber("20002")).thenReturn(targetAccount);

        assertThrows(InsuficientFoundsException.class,
                () -> accountTransferService.transfer("10001", "20002", BigDecimal.valueOf(60)));

        assertEquals(BigDecimal.valueOf(50).doubleValue(), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(300).doubleValue(), targetAccount.getBalance());
        verify(accountTransferRepository, never()).makeTransaction(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void shouldAllowTransferWhenAmountMatchesSourceBalanceExactly() {
        Account sourceAccount = accountWithBalance("10001", 100d);
        Account targetAccount = accountWithBalance("20002", 20d);
        BigDecimal amount = BigDecimal.valueOf(100);

        when(accountSearchRepository.findAccountByNumber("10001")).thenReturn(sourceAccount);
        when(accountSearchRepository.findAccountByNumber("20002")).thenReturn(targetAccount);

        accountTransferService.transfer("10001", "20002", amount);

        assertEquals(BigDecimal.ZERO.doubleValue(), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(120).doubleValue(), targetAccount.getBalance());
        verify(accountTransferRepository).makeTransaction(sourceAccount, targetAccount, amount);
    }

    private Account accountWithBalance(String number, Double balance) {
        return Account.builder()
                .id(1L)
                .number(number)
                .balance(balance)
                .build();
    }

}
