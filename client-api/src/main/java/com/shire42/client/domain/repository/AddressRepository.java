package com.shire42.client.domain.repository;

import com.shire42.client.domain.model.Address;
import com.shire42.client.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("from Address as address " +
            "join address.client as client " +
            "where address.isOfficialAddress = true " +
            "and client.id = :clientId")
    Optional<Address> findOfficialAddressByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Query("update Address as address " +
            "set address.isOfficialAddress = false " +
            "where address.client.id = :clientId")
    void updateAllAddressFromUser(@Param("clientId") Long clientId);

}
