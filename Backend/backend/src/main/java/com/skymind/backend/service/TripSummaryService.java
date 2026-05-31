package com.skymind.backend.service;

import com.skymind.backend.dto.FlightOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripSummaryService {

    private final ChatClient chatClient;

    @Cacheable(
            value = "tripSummaryCache",
            key = "T(java.util.Objects).hash(#bestFlight)"
    )
    public String generateSummary(FlightOffer bestFlight) {

        String prompt = String.format("""
                You are SkyMind AI.

                Create a short travel recommendation summary.

                Flight:
                Airline: %s
                Price: %.2f
                Duration: %s
                Stops: %d

                Explain in 2–3 sentences:
                why this is a good option for the traveler.
                """,
                bestFlight.getAirline(),
                bestFlight.getPrice(),
                bestFlight.getDuration(),
                bestFlight.getStops()
        );

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return response
                .replace("\n", " ")
                .replace("\r", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}