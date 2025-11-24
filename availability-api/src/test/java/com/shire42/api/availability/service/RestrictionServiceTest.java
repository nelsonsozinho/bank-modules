package com.shire42.api.availability.service;

// src/test/java/com/shire42/api/availability/service/RestrictionServiceTest.java
import com.shire42.api.availability.model.Client;
import com.shire42.api.availability.model.FinancialRestriction;
import com.shire42.api.availability.repository.ClientRepository;
import com.shire42.api.availability.repository.FinancialRestrictionRepository;
import com.shire42.api.availability.service.impl.RestrictionServiceImpl;
import com.shire42.api.availability.service.dto.RestrictionDto;
import com.shire42.api.availability.service.exception.ClientNotFoundException;
import com.shire42.api.availability.service.exception.RestrictionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestrictionServiceTest {

    @Mock
    FinancialRestrictionRepository repository;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    RestrictionServiceImpl service;

    @Test
    void getRestrictionById_whenFound_returnsDto() {
        FinancialRestriction r = new FinancialRestriction();
        r.setId(1L);
        r.setValue(123.45);
        r.setSource("SRC");
        r.setLastUpdate(LocalDate.of(2024, 1, 1));

        when(repository.findById(1L)).thenReturn(Optional.of(r));

        RestrictionDto dto = service.getRestrictionById(1L);

        assertEquals(1L, dto.getId());
        assertEquals(123.45, dto.getValue());
        assertEquals("SRC", dto.getSource());
        assertEquals(LocalDate.of(2024, 1, 1), dto.getLastUpdate());
    }

    @Test
    void getRestrictionById_whenNotFound_throws() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RestrictionNotFoundException.class, () -> service.getRestrictionById(99L));
    }

    @Test
    void listCreditRestrictions_mapsAll() {
        FinancialRestriction r1 = new FinancialRestriction();
        r1.setId(1L);
        r1.setValue(10.0);
        r1.setSource("A");

        FinancialRestriction r2 = new FinancialRestriction();
        r2.setId(2L);
        r2.setValue(20.0);
        r2.setSource("B");

        when(repository.findByCpf("cpf")).thenReturn(Arrays.asList(r1, r2));

        var list = service.listCreditRestrictions("cpf");
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(d -> d.getId().equals(1L) && d.getValue() == 10.0));
        assertTrue(list.stream().anyMatch(d -> d.getId().equals(2L) && d.getValue() == 20.0));
    }

    @Test
    void addRestriction_whenClientExists_returnsGeneratedId() {
        Client client = new Client();
        client.setId(7L);

        when(clientRepository.findByCpf("cpf")).thenReturn(Optional.of(client));

        // simulate repository.save setting id on the passed entity
        doAnswer(invocation -> {
            FinancialRestriction arg = invocation.getArgument(0);
            arg.setId(42L);
            return arg;
        }).when(repository).save(any(FinancialRestriction.class));

        Long returned = service.addRestriction("cpf", BigDecimal.valueOf(55.5), "src");
        assertEquals(42L, returned);
        verify(repository).save(any(FinancialRestriction.class));
    }

    @Test
    void addRestriction_whenClientNotFound_throws() {
        when(clientRepository.findByCpf("not")).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class,
                () -> service.addRestriction("not", BigDecimal.ONE, "s"));
    }

    @Test
    void removeRestriction_whenFound_deactivatesAndSaves() {
        FinancialRestriction r = new FinancialRestriction();
        r.setId(5L);
        r.setIdActivated(true);

        when(repository.findById(5L)).thenReturn(Optional.of(r));

        service.removeRestriction(5L);

        ArgumentCaptor<FinancialRestriction> captor = ArgumentCaptor.forClass(FinancialRestriction.class);
        verify(repository).save(captor.capture());
        FinancialRestriction saved = captor.getValue();
        assertFalse(saved.getIdActivated());
    }

    @Test
    void removeRestriction_whenNotFound_throws() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RestrictionNotFoundException.class, () -> service.removeRestriction(999L));
    }

    @Test
    void updateRestriction_whenFound_updatesAndSaves() {
        FinancialRestriction r = new FinancialRestriction();
        r.setId(11L);
        r.setValue(1.0);
        r.setSource("old");
        r.setIdActivated(true);
        r.setLastUpdate(LocalDate.of(2020,1,1));

        when(repository.findById(11L)).thenReturn(Optional.of(r));

        service.updateRestriction(11L, BigDecimal.valueOf(99.99), "newSrc", false);

        ArgumentCaptor<FinancialRestriction> captor = ArgumentCaptor.forClass(FinancialRestriction.class);
        verify(repository).save(captor.capture());
        FinancialRestriction saved = captor.getValue();

        assertEquals(99.99, saved.getValue());
        assertEquals("newSrc", saved.getSource());
        assertEquals(LocalDate.now(), saved.getLastUpdate());
        assertFalse(saved.getIdActivated());
    }

    @Test
    void updateRestriction_whenNotFound_throws() {
        when(repository.findById(123L)).thenReturn(Optional.empty());
        assertThrows(RestrictionNotFoundException.class,
                () -> service.updateRestriction(123L, BigDecimal.ONE, "s", true));
    }
}
