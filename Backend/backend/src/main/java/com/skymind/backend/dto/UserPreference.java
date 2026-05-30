package com.skymind.backend.dto;

import lombok.Data;

@Data
public class UserPreference {

    private String preferredAirline;

    private Double maxBudget;

    private Boolean nonStopOnly;

    private String preferredDepartureTime;
}