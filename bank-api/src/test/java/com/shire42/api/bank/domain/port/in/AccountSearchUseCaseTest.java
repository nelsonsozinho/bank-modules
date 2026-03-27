package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.domain.model.Account;
import com.shire42.api.bank.domain.port.out.AccountSearchRepository;
import com.shire42.api.bank.domain.service.AccountSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountSearchUseCaseTest {

    @Mock
    private AccountSearchRepository accountSearchRepository;

    private AccountSearchService accountDepositService;

    @BeforeEach()
    void setup() {
        this.accountDepositService = new AccountSearchService(accountSearchRepository);
    }

    @Test
    void shouldSearchAccountUseCase() {
        Account account = Account.builder()
                .id(1L)
                .number("1234567890")
                .balance(BigDecimal.valueOf(1000).doubleValue())
                .build();

        when(accountSearchRepository.findAccountByNumber("1234567890")).thenReturn(account);

        Account accountFinder = accountDepositService.findAccountByNumber("1234567890");

        assertNotNull(accountFinder);
        assertEquals(accountFinder.getBalance(), account.getBalance());
    }

}
