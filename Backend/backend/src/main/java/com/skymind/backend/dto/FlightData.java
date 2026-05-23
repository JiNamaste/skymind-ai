package com.skymind.backend.dto;

import lombok.Data;

@Data
public class FlightData {

    private Airline airline;

    private Departure departure;

    private Arrival arrival;

    private Flight flight;
}