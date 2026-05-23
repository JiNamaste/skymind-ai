package com.skymind.backend.service;


import com.skymind.backend.dto.*;
import com.skymind.backend.externalApi.AviationstackClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final AviationstackClient client;

    public FlightService(AviationstackClient client) {
        this.client = client;
    }

    public List<FlightResultDto> searchFlights(FlightSearchRequest request) {

        FlightSearchResponse response = client.searchFlights(request.getOrigin());

        List<FlightResultDto> flights =
                response.getData()
                        .stream()
                        .map(FlightMapper::toDto)
                        .collect(Collectors.toList());

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
}