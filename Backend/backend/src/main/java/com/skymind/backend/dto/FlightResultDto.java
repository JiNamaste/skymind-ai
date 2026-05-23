package com.skymind.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightResultDto {

    private String airline;

    private String flightNumber;

    private String departureAirport;

    private String arrivalAirport;

    private String departureTime;

    private String arrivalTime;
}
