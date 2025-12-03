package com.shire42.client.domain.repository;

import com.shire42.client.domain.model.Address;
import com.shire42.client.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
