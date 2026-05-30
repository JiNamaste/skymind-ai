package com.skymind.backend.controller;

import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.FlightRankingResponse;
import com.skymind.backend.service.FlightRankingService;
import com.skymind.backend.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flights", description = "Flight Ranking APIs")
public class FlightRankingController {

    private final FlightService flightSearchService;
    private final FlightRankingService flightRankingService;


    @Operation(summary = "Rank flights based on price, duration, and travel criteria")
    @GetMapping("/rank")
    public FlightRankingResponse rankFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date) {
        List<FlightOffer> flights = flightSearchService.searchFlights(from, to, date);
        return flightRankingService.rankFlights(flights);
    }
}