package com.skymind.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripSummaryResponse {

    private FlightOffer bestFlight;
    private String summary;
}