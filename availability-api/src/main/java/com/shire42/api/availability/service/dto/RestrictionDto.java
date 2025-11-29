package com.shire42.api.availability.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestrictionDto implements Serializable {

    private Long id;
    private String source;
    private Double value;
    private LocalDate lastUpdate;
    private Boolean isActivated;

}
