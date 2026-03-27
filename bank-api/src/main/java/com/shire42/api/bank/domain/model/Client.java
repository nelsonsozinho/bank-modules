package com.shire42.api.bank.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Client {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private List<Account> accounts;

}
