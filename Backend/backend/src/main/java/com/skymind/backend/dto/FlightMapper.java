package com.skymind.backend.dto;



public class FlightMapper {

    public static FlightResultDto toDto(FlightData flight) {

        return FlightResultDto.builder()
                .airline(flight.getAirline().getName())
                .flightNumber(flight.getFlight().getNumber())
                .departureAirport(flight.getDeparture().getAirport())
                .arrivalAirport(flight.getArrival().getAirport())
                .departureTime(flight.getDeparture().getScheduled())
                .arrivalTime(flight.getArrival().getScheduled())
                .build();
    }
}