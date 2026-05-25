package com.skymind.backend.service;

import com.skymind.backend.dto.FlightResultDto;
import com.skymind.backend.dto.FlightScore;
import com.skymind.backend.dto.UserPreferenceProfile;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightScoringService {

    public List<FlightScore> scoreFlights(List<FlightResultDto> flights,String airlinePrefrence, UserPreferenceProfile profile) {

        if (flights == null || flights.isEmpty()) {
            return Collections.emptyList();
        }

        return flights.stream()
                .map(flight -> {
                    int score = calculateScore(flight,airlinePrefrence,profile);
                    return FlightScore.builder()
                            .flight(flight)
                            .score(score)
                            .build();
                })
                .sorted((a, b) -> b.getScore() - a.getScore())
                .collect(Collectors.toList());
    }

    private int calculateScore(FlightResultDto flight,String airlinePrefrence, UserPreferenceProfile profile) {

        int score = 0;

        // Rule 1: Morning flights are better
        if (flight.getDepartureTime() != null && isBetween5And8AM(flight.getDepartureTime())) {
            score += profile.getMorningWeight();
        }

        // Rule 2: Customer preference
        if (flight.getAirline() != null &&
                flight.getAirline().equalsIgnoreCase(airlinePrefrence)) {
            score += profile.getAirlineWeight();
        }

        // Rule 3: Short flights preference (basic heuristic)
        if (flight.getArrivalTime() != null &&
                flight.getDepartureTime() != null) {
            score += 20;
        }

        // Rule 4: Default base score
        score += 10;

        return score;
    }

    private boolean isBetween5And8AM(String departureTime) {
        int hour = OffsetDateTime.parse(departureTime).getHour();
        return hour >= 5 && hour < 8;
    }
}