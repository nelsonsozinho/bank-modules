package com.shire42.api.availability.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ClientDto {

    private Long id;
    private String cpf;
    private String name;
    private List<RestrictionDto> restrictions;

}
