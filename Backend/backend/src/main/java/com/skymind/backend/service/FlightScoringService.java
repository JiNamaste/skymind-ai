package com.skymind.backend.service;

import com.skymind.backend.dto.FlightResultDto;
import com.skymind.backend.dto.FlightScore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightScoringService {

    public List<FlightScore> scoreFlights(List<FlightResultDto> flights,String airlinePrefrence) {

        return flights.stream()
                .map(flight -> {
                    int score = calculateScore(flight,airlinePrefrence);
                    return FlightScore.builder()
                            .flight(flight)
                            .score(score)
                            .build();
                })
                .sorted((a, b) -> b.getScore() - a.getScore())
                .collect(Collectors.toList());
    }

    private int calculateScore(FlightResultDto flight,String airlinePrefrence) {

        int score = 0;

        // Rule 1: Morning flights are better
        if (flight.getDepartureTime() != null &&
                flight.getDepartureTime().contains("06") ||
                flight.getDepartureTime().contains("07") ||
                flight.getDepartureTime().contains("08")) {
            score += 30;
        }

        // Rule 2: Customer preference
        if (flight.getAirline() != null &&
                flight.getAirline().equalsIgnoreCase(airlinePrefrence)) {
            score += 20;
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
}