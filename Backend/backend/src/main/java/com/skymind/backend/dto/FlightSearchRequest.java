package com.skymind.backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FlightSearchRequest {

    @NotBlank(message = "Origin is required")
    private String origin;
    @NotBlank(message = "Destination is required")
    private String destination;
    private String airline;
    private Integer limit = 10;
}