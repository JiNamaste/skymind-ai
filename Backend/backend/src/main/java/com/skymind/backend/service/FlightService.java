package com.skymind.backend.service;

import com.skymind.backend.dto.*;
import com.skymind.backend.externalApi.AviationstackClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final AviationstackClient client;

    public FlightService(AviationstackClient client) {
        this.client = client;
    }

    public List<FlightResultDto> searchFlights(String depIata) {
        FlightSearchResponse response = client.searchFlights(depIata);
        return response.getData().stream().map(FlightMapper::toDto).toList();
    }
}