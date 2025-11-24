package com.shire42.api.availability.service;

import com.shire42.api.availability.model.Client;
import com.shire42.api.availability.model.FinancialRestriction;
import com.shire42.api.availability.repository.ClientRepository;
import com.shire42.api.availability.repository.FinancialRestrictionRepository;
import com.shire42.api.availability.service.dto.ClientDto;
import com.shire42.api.availability.service.dto.RestrictionDto;
import com.shire42.api.availability.service.exception.ClientNotFoundException;
import com.shire42.api.availability.service.impl.ClientServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository repository;

    @Mock
    FinancialRestrictionRepository financialRestrictionRepository;

    @InjectMocks
    ClientServiceImp service;

    @Test
    void getClientByCpf_whenFound_returnsDto_withMappedRestrictions() {
        Client client = new Client();
        client.setId(3L);
        client.setCpf("cpf-123");
        client.setName("John Doe");

        FinancialRestriction fr = new FinancialRestriction();
        fr.setId(7L);
        fr.setValue(12.34);
        fr.setSource("SRC");
        fr.setLastUpdate(LocalDate.of(2024, 1, 2));
        fr.setIdActivated(true);

        client.setRestrictions(List.of(fr));

        when(repository.findByCpf("cpf-123")).thenReturn(Optional.of(client));

        ClientDto dto = service.getClientByCpf("cpf-123");

        assertEquals(3L, dto.getId());
        assertEquals("cpf-123", dto.getCpf());
        assertEquals("John Doe", dto.getName());
        assertNotNull(dto.getRestrictions());
        assertEquals(1, dto.getRestrictions().size());

        RestrictionDto rd = dto.getRestrictions().get(0);
        assertEquals(7L, rd.getId());
        assertEquals(12.34, rd.getValue(), 0.0001);
        assertEquals("SRC", rd.getSource());
        assertEquals(LocalDate.of(2024, 1, 2), rd.getLastUpdate());
    }

    @Test
    void getClientByCpf_whenNotFound_throws() {
        when(repository.findByCpf("missing")).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> service.getClientByCpf("missing"));
    }

    @Test
    void newClient_savesClient_andReturnsDto() {
        Client saved = new Client();
        saved.setId(5L);
        saved.setCpf("new-cpf");
        saved.setName("New Name");

        when(repository.save(any(Client.class))).thenReturn(saved);

        ClientDto dto = service.newClient("new-cpf", "New Name");

        assertEquals("new-cpf", dto.getCpf());
        assertEquals("New Name", dto.getName());
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    void newClientWithRestrictions_returnsClientId_andDoesNotSaveRestrictions() {
        // use a mocked ClientDto to avoid relying on DTO builders/constructors
        ClientDto clientDto = mock(ClientDto.class);
        RestrictionDto r = mock(RestrictionDto.class);
        when(clientDto.getCpf()).thenReturn("cpf-x");
        when(clientDto.getName()).thenReturn("Name X");
        when(clientDto.getRestrictions()).thenReturn(List.of(r));
        when(r.getSource()).thenReturn("SRC");
        when(r.getValue()).thenReturn(BigDecimal.valueOf(42).toBigInteger().doubleValue());

        Client saved = new Client();
        saved.setId(11L);
        saved.setCpf("cpf-x");
        saved.setName("Name X");

        when(repository.save(any(Client.class))).thenReturn(saved);

        Long id = service.newClientWithRestrictions(clientDto);

        assertEquals(11L, id);
        verify(repository, times(1)).save(any(Client.class));
        // current implementation builds restrictions but does not persist them
        verify(financialRestrictionRepository, never()).saveAll(anyList());
    }
}
