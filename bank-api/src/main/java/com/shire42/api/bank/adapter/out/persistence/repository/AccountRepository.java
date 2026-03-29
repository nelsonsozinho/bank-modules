package com.shire42.api.bank.adapter.out.persistence.repository;

import com.shire42.api.bank.adapter.out.persistence.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("select a " +
            "from AccountEntity a " +
            "join fetch a.client " +
            "where a.number = :accountNumber")
    AccountEntity findByNumber(@Param("accountNumber") final String accountNumber);

}
