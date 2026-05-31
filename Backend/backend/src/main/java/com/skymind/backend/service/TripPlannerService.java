package com.skymind.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skymind.backend.dto.tripPlan.TripPlanRequest;
import com.skymind.backend.dto.tripPlan.TripPlanResponse;
import com.skymind.backend.utility.TripPromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripPlannerService {

    private final ChatClient chatClient;
    private final TripPromptBuilder tripPromptBuilder;
    private final ObjectMapper objectMapper;

    @Cacheable(
            value = "tripPlanCache",
            key = "T(java.util.Objects).hash(#request)"
    )
    public TripPlanResponse generateTripPlan(TripPlanRequest request) {
        try {
            String prompt = tripPromptBuilder.buildTripPlannerPrompt(request);

            String aiResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            String cleanedResponse = cleanJsonResponse(aiResponse);
            return objectMapper.readValue(cleanedResponse, TripPlanResponse.class);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate trip plan: " + ex.getMessage());
        }
    }
    private String cleanJsonResponse(String response) {
        return response
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }
}