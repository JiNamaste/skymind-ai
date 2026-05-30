package com.skymind.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightRankingResponse {

    private FlightOffer cheapest;
    private FlightOffer fastest;
    private FlightOffer bestOverall;
}