package com.shire42.client.domain.rest.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ClientDto {

    private Long clientId;

    private String name;

    private String cpf;

    private String rg;

    private String email;

    private List<AddressDto> addresses;

}
