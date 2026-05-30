package com.skymind.backend.controller;

import com.skymind.backend.dto.*;
import com.skymind.backend.service.FlightAiService;
import com.skymind.backend.service.FlightFilterService;
import com.skymind.backend.service.FlightRecommendationService;
import com.skymind.backend.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flights", description = "Flight Search APIs")
public class FlightController {

    private final FlightService flightService;
    private final FlightRecommendationService recommendationService;
    @Autowired
    private FlightFilterService flightFilterService;
    @Autowired
    private FlightAiService flightAiService;

    public FlightController(FlightService flightService,FlightRecommendationService recommendationService) {
        this.flightService = flightService;
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Search available flights based on origin, destination, airline, and travel preferences")
    @PostMapping("/search")
    public List<FlightResultDto> searchFlights(@Valid @RequestBody FlightSearchRequest request) {
        return flightService.searchFlights(request);
    }

    @Operation(summary = "Generate personalized flight recommendations using travel preferences and search criteria")
    @PostMapping("/recommend")
    public RecommendationResponse recommend(@RequestBody FlightSearchRequest request) {
        return flightService.getRecommendations(request);
    }
    @Operation(summary = "Get the best flight recommendation by origin, destination, and travel date")
    @GetMapping("/recommend")
    public FlightRecommendation recommendFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date) {

        List<FlightOffer> flights = flightService.searchFlights(from, to, date);
        return recommendationService.recommend(flights);
    }
    @Operation(summary = "Get AI-powered flight recommendation with explanation by origin, destination, and date")
    @GetMapping("/recommend-ai")
    public ResponseEntity<AiRecommendationResponse> recommendWithAi(@RequestParam String from, @RequestParam String to, @RequestParam String date) {
        List<FlightOffer> flights = flightService.searchFlights(from, to, date);
        if (flights == null || flights.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        AiRecommendationResponse response = flightAiService.explainRecommendation(flights);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Filter available flights by route, date, and user-defined criteria")
    @PostMapping("/filter")
    public List<FlightOffer> filterFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date, @RequestBody FlightFilterRequest filterRequest) {
        List<FlightOffer> flights = flightService.searchFlights(from, to, date);
        return flightFilterService.filter(flights, filterRequest);
    }
}