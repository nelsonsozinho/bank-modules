package com.shire42.api.bank.controllers;

import com.shire42.api.bank.domain.model.rest.in.AccountTransactionInRest;
import com.shire42.api.bank.domain.model.rest.in.ClientAccountRest;
import com.shire42.api.bank.domain.model.rest.in.DepositTransactionRest;
import com.shire42.api.bank.domain.model.rest.out.AccountOutRest;
import com.shire42.api.bank.domain.model.rest.out.AccountTransactionOutRest;
import com.shire42.api.bank.domain.model.rest.out.ClientAccountOutRest;
import com.shire42.api.bank.domain.service.AccountService;
import com.shire42.api.bank.domain.service.transaction.TransactionType;
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

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountOutRest> getAccountByNumber(@PathVariable("accountNumber") final String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountTransactionOutRest> transferTransaction(@RequestBody final AccountTransactionInRest transferObject) {
        accountService.makeTransaction(transferObject.getSourceAccount(), transferObject.getTargetAccount(), transferObject.getAmount());
        var response = AccountTransactionOutRest.builder()
                .status("SUCCESS")
                .message("Transaction completed")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/new")
    public ResponseEntity<ClientAccountOutRest> createNewAccount(@RequestBody final ClientAccountRest clientAccount) {
        //TODO implements it later
        return null;
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountTransactionOutRest> deposit(@RequestBody final DepositTransactionRest deposit) {
        accountService.makeDeposit(deposit.getClientId(), deposit.getAccountNumber(), deposit.getAmount(), TransactionType.DEPOSIT);
        var response = AccountTransactionOutRest.builder()
                .status("SUCCESS")
                .message("Deposit completed")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<AccountTransactionOutRest> withdrawal(@RequestBody final DepositTransactionRest deposit) {
        accountService.makeWithdrawal(deposit.getClientId(), deposit.getAccountNumber(), deposit.getAmount());
        var response = AccountTransactionOutRest.builder()
                .status("SUCCESS")
                .message("Withdrawal completed")
                .build();
        return ResponseEntity.ok(response);
    }

}
