package com.skymind.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightRecommendation {

    private FlightOffer bestOption;
    private FlightOffer cheapest;
    private FlightOffer fastest;
}