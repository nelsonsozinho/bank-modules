package com.shire42.api.availability.service;

import com.shire42.api.availability.service.dto.RestrictionDto;

import java.math.BigDecimal;
import java.util.List;

public interface RestrictionService {

    RestrictionDto getRestrictionById(final Long id);

    List<RestrictionDto> listCreditRestrictions(final String cpf);

    Long addRestriction(final String cpf, final BigDecimal value, final String source);

    void removeRestriction(final Long idRestriction);

    void updateRestriction(final Long id, final BigDecimal value, final String source, final Boolean isActivated);

}
