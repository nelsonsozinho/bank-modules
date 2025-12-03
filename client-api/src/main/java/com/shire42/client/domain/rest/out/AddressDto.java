package com.shire42.client.domain.rest.out;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class AddressDto implements Serializable {

    private Long addressId;

    private String street;

    private String zipCode;

    private String neighborhood;

    private String state;

    private String country;

    private Boolean isOfficialAddress;

}
