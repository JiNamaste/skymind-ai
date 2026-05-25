package com.skymind.backend.service;

import com.skymind.backend.dto.*;
import com.skymind.backend.exception.NoFlightsFoundException;
import com.skymind.backend.externalApi.AviationstackClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final AviationstackClient client;
    private final FlightScoringService scoringService;
    private  final PreferenceParserService preferenceParserService;

    public FlightService(AviationstackClient client, FlightScoringService scoringService,PreferenceParserService preferenceParserService) {
        this.client = client;
        this.scoringService = scoringService;
        this.preferenceParserService = preferenceParserService;
    }

    public List<FlightResultDto> searchFlights(FlightSearchRequest request) {

        FlightSearchResponse response = client.searchFlights(request.getOrigin());

        List<FlightResultDto> flights = response.getData().stream()
                        .map(FlightMapper::toDto).collect(Collectors.toList());

        if (request.getDestination() != null) {
            flights = flights.stream().filter(f -> request.getDestination()
                    .equalsIgnoreCase(f.getArrivalAirport())).toList();
        }
        if (request.getAirline() != null) {
            flights = flights.stream().filter(f -> f.getAirline()
                    .equalsIgnoreCase(request.getAirline())).toList();
        }
        return flights.stream().limit(request.getLimit()).toList();
    }

    public RecommendationResponse getRecommendations(FlightSearchRequest request) {

        FlightSearchResponse response = client.searchFlights(request.getOrigin());
        List<FlightResultDto> flights = response.getData()
                        .stream()
                        .map(FlightMapper::toDto)
                        .toList();
        if (request.getDestination() != null) {
            flights = flights.stream().filter(f -> request.getDestination()
                            .equalsIgnoreCase(f.getArrivalAirport())).toList();
        }
        if (request.getAirline() != null) {
            flights = flights.stream().filter(f -> f.getAirline()
                            .equalsIgnoreCase(request.getAirline())).toList();
        }
        String airline = request.getAirline() != null && !request.getAirline().isBlank() ? request.getAirline() : null;
        UserPreferenceProfile profile = preferenceParserService.parse(request.getPreference());
        //Score
        List<FlightScore> scoredFlights = scoringService.scoreFlights(flights ,airline,profile);
        if (flights == null || flights.isEmpty()) {
            throw new NoFlightsFoundException("No flights found between "+ request.getOrigin() +" to " + request.getDestination());
        }

        return RecommendationResponse.builder()
                .recommended(scoredFlights.get(0))
                .top3(scoredFlights.stream().limit(3).toList())
                .build();
    }


    public List<FlightOffer> searchFlights(String from, String to, String date) {
        return client.searchFlights(from, to, date);
    }



}