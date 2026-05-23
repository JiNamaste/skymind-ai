package com.skymind.backend.service;

import com.skymind.backend.dto.FlightSearchResponse;
import com.skymind.backend.externalApi.AviationstackClient;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final AviationstackClient aviationstackClient;

    public FlightService(AviationstackClient aviationstackClient) {
        this.aviationstackClient = aviationstackClient;
    }

    public FlightSearchResponse searchFlights(String departureIata) {
        return aviationstackClient.searchFlights(departureIata);
    }
}