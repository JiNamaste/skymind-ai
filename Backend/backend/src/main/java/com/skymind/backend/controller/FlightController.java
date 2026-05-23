package com.skymind.backend.controller;

import com.skymind.backend.dto.FlightResultDto;
import com.skymind.backend.dto.FlightSearchRequest;
import com.skymind.backend.service.FlightService;
import jakarta.validation.Valid;
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
    public List<FlightResultDto> searchFlights(@Valid @RequestBody FlightSearchRequest request) {
        return flightService.searchFlights(request);
    }
}