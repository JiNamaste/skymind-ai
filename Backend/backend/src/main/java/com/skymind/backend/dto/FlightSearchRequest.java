package com.skymind.backend.dto;


import lombok.Data;

@Data
public class FlightSearchRequest {

    private String origin;
    private String destination;
    private String airline;
    private Integer limit = 10;
}