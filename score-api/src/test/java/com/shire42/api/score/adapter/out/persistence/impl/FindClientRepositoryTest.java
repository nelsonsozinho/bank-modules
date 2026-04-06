package com.shire42.api.score.adapter.out.persistence.impl;

import com.shire42.api.score.adapter.out.persistence.model.ClientEntity;
import com.shire42.api.score.adapter.out.persistence.repository.ClientRepository;
import com.shire42.api.score.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    private FindClientAdapter findClientAdapter;

    @BeforeEach
    void setup() {
        this.findClientAdapter = new FindClientAdapter(clientRepository);
    }

    @Test
    void findClientByCpfWithSuccess() {
        String cpf = "00000000000";
        ClientEntity entity = new ClientEntity();
        entity.setId(1L);
        entity.setName("John Doe");
        entity.setEmail("john.doe@mail.com");
        entity.setCpf(cpf);

        when(clientRepository.findClientByCpf(cpf)).thenReturn(Optional.of(entity));

        Client result = findClientAdapter.findClientByCpf(cpf);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getEmail(), result.getEmail());
        assertEquals(entity.getCpf(), result.getCpf());
    }

    @Test
    void throwExceptionWhenClientIsNotFound() {
        String cpf = "99999999999";
        when(clientRepository.findClientByCpf(cpf)).thenReturn(Optional.empty());

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> findClientAdapter.findClientByCpf(cpf));

        assertEquals("Client not found with CPF: " + cpf, result.getMessage());
    }

}

