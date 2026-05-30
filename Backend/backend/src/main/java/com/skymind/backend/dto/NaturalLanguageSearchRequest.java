package com.skymind.backend.dto;


import lombok.Data;

@Data
public class NaturalLanguageSearchRequest {

    private String from;
    private String to;
    private String date;
    private String preferredAirline;
    private Double maxBudget;
    private Boolean nonStopOnly;
}