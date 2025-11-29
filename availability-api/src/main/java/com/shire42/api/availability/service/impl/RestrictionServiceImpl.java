package com.shire42.api.availability.service.impl;

import com.shire42.api.availability.model.Client;
import com.shire42.api.availability.model.FinancialRestriction;
import com.shire42.api.availability.repository.ClientRepository;
import com.shire42.api.availability.repository.FinancialRestrictionRepository;
import com.shire42.api.availability.service.RestrictionService;
import com.shire42.api.availability.service.dto.RestrictionDto;
import com.shire42.api.availability.service.exception.ClientNotFoundException;
import com.shire42.api.availability.service.exception.RestrictionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestrictionServiceImpl implements RestrictionService {

    private final FinancialRestrictionRepository repository;

    private final ClientRepository clientRepository;

    @Override
    @Cacheable(value = "restrictionCache", key = "#restrictionId")
    public RestrictionDto getRestrictionById(final Long restrictionId) {
        final FinancialRestriction restriction = repository.findById(restrictionId).orElseThrow(() ->
                new RestrictionNotFoundException(String.format("Restriction with id %s not fount", restrictionId)));
        return RestrictionDto.builder()
                .lastUpdate(restriction.getLastUpdate())
                .source(restriction.getSource())
                .value(restriction.getValue())
                .id(restriction.getId())
                .build();
    }

    @Override
    @Cacheable(value = "listRestrictionsCache", key = "#cpf")
    public List<RestrictionDto> listCreditRestrictions(final String cpf) {
        return repository.findByCpf(cpf).stream()
                .map(r -> RestrictionDto.builder()
                        .value(r.getValue())
                        .id(r.getId())
                        .source(r.getSource())
                        .lastUpdate(r.getLastUpdate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long addRestriction(final String cpf, final BigDecimal value, final String source) {
        final Client client = clientRepository.findByCpf(cpf).orElseThrow(
                () -> new ClientNotFoundException(String.format("Client with cpf %s not found", cpf)));
        FinancialRestriction newRestriction = new FinancialRestriction();
        newRestriction.setLastUpdate(LocalDate.now());
        newRestriction.setClient(client);
        newRestriction.setSource(source);
        newRestriction.setIdActivated(true);
        newRestriction.setValue(value.doubleValue());
        repository.save(newRestriction);
        return newRestriction.getId();
    }

    @Override
    @Transactional
    public void removeRestriction(final Long idRestriction) {
        final FinancialRestriction restriction = repository.findById(idRestriction).orElseThrow(
                () -> new RestrictionNotFoundException(String.format("Restriction with id %s not fount", idRestriction)));
        restriction.setIdActivated(false);
        repository.save(restriction);
    }

    @Override
    @Transactional
    public void updateRestriction(final Long id, final BigDecimal value, final String source, final Boolean isActivated) {
        final FinancialRestriction restriction = repository.findById(id).orElseThrow(
                () -> new RestrictionNotFoundException(String.format("Restriction with id %s not fount", id)));
        restriction.setValue(value.doubleValue());
        restriction.setSource(Strings.isNotBlank(source) ? source : restriction.getSource());
        restriction.setLastUpdate(LocalDate.now());
        restriction.setIdActivated(isActivated);
        repository.save(restriction);
    }

}
