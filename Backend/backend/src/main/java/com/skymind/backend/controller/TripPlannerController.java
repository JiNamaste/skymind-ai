package com.skymind.backend.controller;

import com.skymind.backend.dto.tripPlan.TripPlanRequest;
import com.skymind.backend.dto.tripPlan.TripPlanResponse;
import com.skymind.backend.service.TripPlannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
@Tag(
        name = "AI Trip Planner",
        description = "APIs for generating AI-powered travel itineraries and budget-based trip plans"
)
public class TripPlannerController {

    private final TripPlannerService tripPlannerService;
    @Operation(
            summary = "Generate AI Trip Plan",
            description = "Generates a personalized AI-powered trip plan based on source, destination, budget, number of days, and traveller type. The plan includes trip summary, budget breakdown, day-wise itinerary, and travel tips using Ollama Llama 3.1."
    )
    @PostMapping("/plan")
    public TripPlanResponse generateTripPlan(@RequestBody TripPlanRequest request) {
        return tripPlannerService.generateTripPlan(request);
    }
}