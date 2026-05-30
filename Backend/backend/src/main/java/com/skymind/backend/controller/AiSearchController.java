package com.skymind.backend.controller;



import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.NaturalLanguageSearchRequest;
import com.skymind.backend.service.AiSearchParserService;
import com.skymind.backend.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai-search")
@RequiredArgsConstructor
@Tag(name = "Flights", description = "AI Recommendation APIs")
public class AiSearchController {

    private final AiSearchParserService aiSearchParserService;
    private final FlightService flightSearchService;
    @Operation(
            summary = "Search flights using natural language",
            description = "Accepts a natural language travel request, extracts travel details using AI, and returns matching flights."
    )
    @PostMapping
    public List<FlightOffer> search(@RequestBody String userPrompt) {
        NaturalLanguageSearchRequest request = aiSearchParserService.parse(userPrompt);
        return flightSearchService.searchFlights(request.getFrom(), request.getTo(), request.getDate());
    }
}