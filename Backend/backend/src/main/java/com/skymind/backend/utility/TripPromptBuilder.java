package com.skymind.backend.utility;

import com.skymind.backend.dto.tripPlan.TripPlanRequest;
import org.springframework.stereotype.Component;

@Component
public class TripPromptBuilder {

    public String buildTripPlannerPrompt(TripPlanRequest request) {

        return """
                You are an AI travel planner.

                Create a practical trip plan using the given details.

                Source: %s
                Destination: %s
                Budget: INR %d
                Days: %d
                Traveller Type: %s

                Return only valid JSON.
                Do not include markdown.
                Do not include explanation outside JSON.

                JSON format:
                {
                  "destination": "string",
                  "days": number,
                  "summary": "string",
                  "budgetBreakdown": {
                    "flight": number,
                    "hotel": number,
                    "food": number,
                    "localTravel": number,
                    "activities": number
                  },
                  "itinerary": [
                    {
                      "day": number,
                      "title": "string",
                      "activities": ["string"],
                      "estimatedCost": "string"
                    }
                  ],
                  "travelTips": ["string"]
                }
                """.formatted(
                request.getSource(),
                request.getDestination(),
                request.getBudget(),
                request.getDays(),
                request.getTravellerType()
        );
    }
}