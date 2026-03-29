package com.shire42.api.bank.adapter.in.web.controllers;

import com.shire42.api.bank.adapter.in.AccountTransactionInRest;
import com.shire42.api.bank.adapter.in.DepositTransactionRest;
import com.shire42.api.bank.adapter.out.dto.AccountOutRest;
import com.shire42.api.bank.adapter.out.dto.AccountTransactionOutRest;
import com.shire42.api.bank.domain.port.in.AccountDepositUseCase;
import com.shire42.api.bank.domain.port.in.AccountTransferUseCase;
import com.shire42.api.bank.domain.port.in.AccountWithdrawalUseCase;
import com.shire42.api.bank.service.AccountService;
import com.shire42.api.bank.adapter.out.persistence.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountDepositUseCase useCase;

    private final AccountWithdrawalUseCase withdrawalUseCase;

    private final AccountTransferUseCase accountTransferUseCase;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountOutRest> getAccountByNumber(@PathVariable("accountNumber") final String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountTransactionOutRest> transferTransaction(@RequestBody final AccountTransactionInRest transferObject) {
        accountTransferUseCase.transfer(transferObject.getSourceAccount(), transferObject.getTargetAccount(), transferObject.getAmount());
        var response = AccountTransactionOutRest.builder()
                .status("SUCCESS")
                .message("Transaction completed")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountTransactionOutRest> deposit(@RequestBody final DepositTransactionRest deposit) {
        useCase.makeDeposit(deposit.getClientId(), deposit.getAccountNumber(), deposit.getAmount(), TransactionType.DEPOSIT);
        var response = AccountTransactionOutRest.builder()
                .status("SUCCESS")
                .message("Deposit completed")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<AccountTransactionOutRest> withdrawal(@RequestBody final DepositTransactionRest deposit) {
        withdrawalUseCase.makeWithdrawal(deposit.getClientId(), deposit.getAccountNumber(), deposit.getAmount());
        var response = AccountTransactionOutRest.builder()
                .status("SUCCESS")
                .message("Withdrawal completed")
                .build();
        return ResponseEntity.ok(response);
    }

}
