package com.skymind.backend.service;

import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.FlightRecommendation;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FlightRecommendationService {

    public FlightRecommendation recommend(List<FlightOffer> flights) {

        if (flights == null || flights.isEmpty()) {
            return null;
        }

        FlightOffer cheapest = flights.stream()
                .min(Comparator.comparing(FlightOffer::getPrice))
                .orElse(null);

        FlightOffer fastest = flights.stream()
                .min(Comparator.comparing(FlightOffer::getDuration))
                .orElse(null);

        FlightOffer bestOption = flights.stream()
                .sorted(Comparator
                        .comparing(FlightOffer::getStops)
                        .thenComparing(FlightOffer::getDuration))
                .findFirst()
                .orElse(null);

        return FlightRecommendation.builder()
                .bestOption(bestOption)
                .cheapest(cheapest)
                .fastest(fastest)
                .build();
    }
}