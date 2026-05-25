package com.skymind.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightOffer {

    private String airline;

    private String flightNumber;

    private String departureAirport;

    private String arrivalAirport;

    private String departureTime;

    private String arrivalTime;

    // in minutes
    private Integer duration;

    // e.g. 0, 1, 2
    private Integer stops;

    // total price
    private Double price;

    private String currency;
}