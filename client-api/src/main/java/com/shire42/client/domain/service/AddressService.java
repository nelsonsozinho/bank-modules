package com.shire42.client.domain.service;

import com.shire42.client.domain.exception.ClientAddressNotFountException;
import com.shire42.client.domain.exception.ClientNotFountException;
import com.shire42.client.domain.mapping.AddressMapping;
import com.shire42.client.domain.model.Address;
import com.shire42.client.domain.model.Client;
import com.shire42.client.domain.repository.AddressRepository;
import com.shire42.client.domain.repository.ClientRepository;
import com.shire42.client.domain.rest.in.AddressRest;
import com.shire42.client.domain.rest.out.AddressDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final ClientRepository clientRepository;

    private final AddressMapping addressMapping;

    @Transactional
    public AddressDto saveNewAddress(AddressRest addressRest, Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new ClientNotFountException(String.format("Client with id %d is not found", clientId)));
        Address address = addressMapping.addressRestToAddress(addressRest);
        address.setClient(client);

        validateOfficialAddress(address.getIsOfficialAddress(), clientId);
        Address newAddress = addressRepository.save(address);

        return addressMapping.addressToAddressDto(newAddress);
    }

    public void validateOfficialAddress(Boolean isOfficialAddress, Long clientId) {
        Optional<Address> address = addressRepository.findOfficialAddressByClientId(clientId);
        if (address.isPresent() && isOfficialAddress) {
            addressRepository.updateAllAddressFromUser(clientId);
        }
    }

    public AddressDto getOfficialClientByAddress(Long clientId) {
        Address address = addressRepository.findOfficialAddressByClientId(clientId).orElseThrow(
                () -> new ClientAddressNotFountException(String.format("Address from the client with id %d was not found",clientId)));
        return addressMapping.addressToAddressDto(address);
    }

    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ClientAddressNotFountException(String.format("Address with id %d was not found",id)));
        return addressMapping.addressToAddressDto(address);
    }

}
