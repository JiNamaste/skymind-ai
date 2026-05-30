package com.skymind.backend.controller;

import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.FlightRankingResponse;
import com.skymind.backend.dto.TripSummaryResponse;
import com.skymind.backend.service.FlightRankingService;
import com.skymind.backend.service.FlightService;
import com.skymind.backend.service.TripSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
@Tag(name = "AI Summary", description = "Trip Summary APIs")
public class TripSummaryController {

    private final FlightService flightSearchService;
    private final FlightRankingService flightRankingService;
    private final TripSummaryService tripSummaryService;
    @Operation(summary = "Generate AI trip summary")
    @GetMapping("/summary")
    public TripSummaryResponse getSummary(@RequestParam String from, @RequestParam String to, @RequestParam String date) {

        List<FlightOffer> flights = flightSearchService.searchFlights(from, to, date);
        FlightRankingResponse ranking = flightRankingService.rankFlights(flights);
        FlightOffer bestFlight = ranking.getBestOverall();
        String summary = tripSummaryService.generateSummary(bestFlight);
        return new TripSummaryResponse(bestFlight, summary);
    }
}