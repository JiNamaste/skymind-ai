package com.skymind.backend.controller;

import com.skymind.backend.dto.FlightResultDto;
import com.skymind.backend.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @GetMapping("/search")
    public List<FlightResultDto> searchFlights(@RequestParam String depIata) {
        return flightService.searchFlights(depIata);
    }
}