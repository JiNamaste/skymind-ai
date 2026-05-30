package com.skymind.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skymind.backend.dto.AiRecommendationResponse;
import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.UserPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightAiService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final UserPreferenceService userPreferenceService;

    public AiRecommendationResponse explainRecommendation(List<FlightOffer> flights) {

        try {

            String prompt = buildPrompt(flights);
            String response = chatClient.prompt()
                            .user(prompt)
                            .call()
                            .content();
            return objectMapper.readValue(response, AiRecommendationResponse.class);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse AI recommendation", ex);
        }
    }

    private String buildPrompt(List<FlightOffer> flights) {

        UserPreference preference = userPreferenceService.getPreference();

        StringBuilder sb = new StringBuilder();

        sb.append("""
        You are SkyMind AI, a flight recommendation assistant.

        Your task:
        Select the single best flight from the available options based on:
        - lowest price
        - shortest duration
        - fewest stops
        - user preferences

        Important rules:
        - Return ONLY valid JSON
        - Do NOT use markdown
        - Do NOT wrap the response in ``` or ```json
        - Do NOT add explanations outside the JSON
        - Output must begin with { and end with }

        JSON response format:

        {
          "recommendedAirline": "string",
          "price": number,
          "duration": "string",
          "stops": number,
          "reason": "string"
        }

        The "reason" should be short and explain why this flight was selected.

        User Preferences:
        """);

        if (preference != null) {
            sb.append(String.format("""
            Preferred Airline: %s
            Max Budget: %s
            Non Stop Only: %s
            Preferred Departure Time: %s

            """,
                    preference.getPreferredAirline() != null ? preference.getPreferredAirline() : "No preference",
                    preference.getMaxBudget() != null ? preference.getMaxBudget() : "No limit",
                    preference.getNonStopOnly() != null ? preference.getNonStopOnly() : "No preference",
                    preference.getPreferredDepartureTime() != null ? preference.getPreferredDepartureTime() : "Any"
            ));
        } else {
            sb.append("""
            No user preferences provided.

            """);
        }

        sb.append("Available Flights:\n\n");

        for (FlightOffer flight : flights) {
            sb.append(String.format("""
            Airline: %s
            Price: %.2f
            Duration: %s
            Stops: %d

            """,
                    flight.getAirline(),
                    flight.getPrice(),
                    flight.getDuration(),
                    flight.getStops()
            ));
        }

        sb.append("""
        Choose the best flight and return ONLY the JSON object.
        """);

        return sb.toString();
    }
}