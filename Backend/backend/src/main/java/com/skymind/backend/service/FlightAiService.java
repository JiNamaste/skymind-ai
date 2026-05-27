package com.skymind.backend.service;

import com.skymind.backend.dto.FlightOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightAiService {

    private final ChatClient chatClient;

    public String explainRecommendation(List<FlightOffer> flights) {

        String prompt = buildPrompt(flights);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    private String buildPrompt(List<FlightOffer> flights) {

        StringBuilder sb = new StringBuilder();

        sb.append("""
        You are SkyMind AI.

        Analyze the flight options below.

        Return ONLY valid JSON.

        Format:

        {
          "recommendedAirline": "string",
          "price": number,
          "duration": "string",
          "stops": number,
          "reason": "string"
        }

        Do not add markdown.
        Do not add explanation outside JSON.

        Flights:
        """);

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

        return sb.toString();
    }
}