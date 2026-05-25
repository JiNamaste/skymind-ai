package com.skymind.backend.dto;

import lombok.Data;

@Data
public class FlightFilterRequest {

    private Double maxPrice;

    private Boolean nonStop;

    private String airline;
}