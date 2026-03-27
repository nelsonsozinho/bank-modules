package com.shire42.api.bank.domain.port.out;

import com.shire42.api.bank.domain.model.Account;

public interface AccountSearchRepository {

    Account findAccountByNumber(String number);

    Account findAccountById(Long id);

}
