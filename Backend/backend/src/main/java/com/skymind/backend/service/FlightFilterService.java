package com.skymind.backend.service;

import com.skymind.backend.dto.FlightFilterRequest;
import com.skymind.backend.dto.FlightOffer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightFilterService {

    public List<FlightOffer> filter(List<FlightOffer> flights, FlightFilterRequest request) {

        return flights.stream()
                .filter(flight ->
                        request.getMaxPrice() == null ||
                                flight.getPrice() <= request.getMaxPrice())

                .filter(flight ->
                        request.getNonStop() == null ||
                                !request.getNonStop() ||
                                flight.getStops() == 0)

                .filter(flight ->
                        request.getAirline() == null ||
                                flight.getAirline()
                                        .equalsIgnoreCase(request.getAirline()))

                .collect(Collectors.toList());
    }
}
