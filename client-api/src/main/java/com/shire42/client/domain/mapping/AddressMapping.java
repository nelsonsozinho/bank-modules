package com.shire42.client.domain.mapping;

import com.shire42.client.domain.model.Address;
import com.shire42.client.domain.rest.in.AddressRest;
import com.shire42.client.domain.rest.out.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapping {

    public Address addressRestToAddress(AddressRest address) {
        return Address.builder()
                .country(address.country())
                .id(address.idAddress())
                .isOfficialAddress(address.isOfficialAddress())
                .neighborhood(address.neighborhood())
                .country(address.country())
                .state(address.state())
                .zipCode(address.zipCode())
                .street(address.street())
                .build();
    }

    public AddressDto addressToAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .idAddress(address.getId())
                .isOfficialAddress(address.getIsOfficialAddress())
                .neighborhood(address.getNeighborhood())
                .state(address.getState())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .build();
    }

}
