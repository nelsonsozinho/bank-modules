package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.domain.model.Account;

public interface AccountSearchUseCase {

    Account findAccountByNumber(String number);

    Account findAccountById(Long id);

}
