package com.skymind.backend.service;

import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.FlightRankingResponse;
import com.skymind.backend.dto.UserPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightRankingService {

    private final UserPreferenceService userPreferenceService;

    public FlightRankingResponse rankFlights(List<FlightOffer> flights) {

        UserPreference pref = userPreferenceService.getPreference();

        List<FlightOffer> filteredFlights = applyPreferenceFilters(flights, pref);

        if (filteredFlights.isEmpty()) {
            filteredFlights = flights;
        }

        FlightOffer cheapest = getCheapest(filteredFlights);

        FlightOffer fastest = getFastest(filteredFlights);

        FlightOffer bestOverall = getBestOverall(filteredFlights, pref);

        return new FlightRankingResponse(cheapest, fastest, bestOverall);
    }

    private List<FlightOffer> applyPreferenceFilters(List<FlightOffer> flights, UserPreference pref) {

        if (pref == null) {
            return flights;
        }

        return flights.stream()
                .filter(f -> pref.getMaxBudget() == null ||
                        f.getPrice() <= pref.getMaxBudget())
                .filter(f -> pref.getNonStopOnly() == null ||
                        !pref.getNonStopOnly() ||
                        f.getStops() == 0)
                .collect(Collectors.toList());
    }

    private FlightOffer getCheapest(List<FlightOffer> flights) {

        return flights.stream().min(Comparator.comparing(FlightOffer::getPrice)).orElse(null);
    }

    private FlightOffer getFastest(List<FlightOffer> flights) {

        return flights.stream().min(Comparator.comparing(FlightOffer::getDuration)).orElse(null);
    }

    private FlightOffer getBestOverall(List<FlightOffer> flights, UserPreference pref) {

        if (pref != null &&
                pref.getPreferredAirline() != null) {

            return flights.stream().filter(f -> f.getAirline().equalsIgnoreCase(pref.getPreferredAirline()))
                    .findFirst()
                    .orElse(getCheapest(flights));
        }

        return getCheapest(flights);
    }
}