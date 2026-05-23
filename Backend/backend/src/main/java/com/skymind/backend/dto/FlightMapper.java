package com.skymind.backend.dto;



public class FlightMapper {

    public static FlightResultDto toDto(FlightData flight) {

        return FlightResultDto.builder()
                .airline(flight.getAirline().getName())
                .flightNumber(flight.getFlight().getNumber())
                .departureAirport(flight.getDeparture().getIata())
                .arrivalAirport(flight.getArrival().getIata())
                .departureTime(flight.getDeparture().getScheduled())
                .arrivalTime(flight.getArrival().getScheduled())
                .build();
    }
}