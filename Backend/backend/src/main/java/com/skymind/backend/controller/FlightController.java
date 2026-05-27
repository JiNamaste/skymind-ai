package com.skymind.backend.controller;

import com.skymind.backend.dto.*;
import com.skymind.backend.service.FlightAiService;
import com.skymind.backend.service.FlightFilterService;
import com.skymind.backend.service.FlightRecommendationService;
import com.skymind.backend.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
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


    @GetMapping("/search")
    public List<FlightResultDto> searchFlights(@Valid @RequestBody FlightSearchRequest request) {
        return flightService.searchFlights(request);
    }

    @PostMapping("/recommend")
    public RecommendationResponse recommend(@RequestBody FlightSearchRequest request) {
        return flightService.getRecommendations(request);
    }

    @GetMapping("/recommend")
    public FlightRecommendation recommendFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date) {

        List<FlightOffer> flights = flightService.searchFlights(from, to, date);
        return recommendationService.recommend(flights);
    }

    @GetMapping("/recommend-ai")
    public ResponseEntity<String> recommendWithAi(@RequestParam String from, @RequestParam String to, @RequestParam String date) {
        List<FlightOffer> flights = flightService.searchFlights(from, to, date);
        if (flights == null || flights.isEmpty()) {
            return ResponseEntity.ok("No flights found for selected route.");
        }
        String aiResponse = flightAiService.explainRecommendation(flights);
        return ResponseEntity.ok(aiResponse);
    }

    @PostMapping("/filter")
    public List<FlightOffer> filterFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date, @RequestBody FlightFilterRequest filterRequest) {

        List<FlightOffer> flights = flightService.searchFlights(from, to, date);
        return flightFilterService.filter(flights, filterRequest);
    }
}