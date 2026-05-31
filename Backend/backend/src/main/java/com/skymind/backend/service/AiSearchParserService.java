package com.skymind.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skymind.backend.dto.NaturalLanguageSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiSearchParserService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public NaturalLanguageSearchRequest parse(String userPrompt) {

        try {

            String prompt = """
You are an API that converts natural language travel requests into JSON.

Extract:
- from
- to
- date
- preferredAirline
- maxBudget
- nonStopOnly

Return ONLY valid JSON.

Rules:
- No markdown
- No code fences
- No explanation text
- Start with {
- End with }
- Missing values should be null.
- For "from" and "to", always return IATA airport codes.
- If the user provides a city name (e.g. Delhi, Mumbai, Bangalore, Chennai, Hyderabad, Kolkata), convert it to its primary airport IATA code.
- Examples:
  - Delhi -> DEL
  - Mumbai -> BOM
  - Bangalore -> BLR
  - Bengaluru -> BLR
  - Chennai -> MAA
  - Hyderabad -> HYD
  - Kolkata -> CCU
  - Pune -> PNQ
- If the user already provides a valid 3-letter IATA code, keep it unchanged.
- The values of "from" and "to" must always be uppercase IATA codes when identifiable.

User Request:
""" + userPrompt;

            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            String cleaned = response
                    .replaceAll("^```json\\s*", "")
                    .replaceAll("^```\\s*", "")
                    .replaceAll("\\s*```$", "")
                    .trim();

            return objectMapper.readValue(
                    cleaned,
                    NaturalLanguageSearchRequest.class
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse travel request", e);
        }
    }
}