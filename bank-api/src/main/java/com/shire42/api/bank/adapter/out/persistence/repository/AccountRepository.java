package com.shire42.api.bank.adapter.out.persistence.repository;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByNumber(final String accountNumber);

}
