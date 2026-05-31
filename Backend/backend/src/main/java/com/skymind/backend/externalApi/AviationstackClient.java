package com.skymind.backend.externalApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.FlightSearchResponse;
import com.skymind.backend.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AviationstackClient {

    @Value("${aviationstack.api.key}")
    private String apiKey;

    @Value("${aviationstack.base.url}")
    private String baseUrl;

    @Value("${serpApi.token}")
    private String serpApiKey;

    @Value("${serpApi.base.url}")
    private String serpApiBaseURL;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AviationstackClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Cacheable(value = "flightSearchCache", key = "#depIata")
    public FlightSearchResponse searchFlights(String depIata) {
        try {
            log.info("AviationStack api is called");
            String url = baseUrl + "/flights?access_key=" + apiKey + "&dep_iata=" + depIata;
            return restTemplate.getForObject(url, FlightSearchResponse.class);
        } catch (Exception ex) {
            throw new ExternalApiException("Flight API failed: " + ex.getMessage());
        }
    }
    @Cacheable(
            value = "flights", key = "#from + '-' + #to + '-' + #date"
    )
    public List<FlightOffer> searchFlights(String from, String to, String date) {

        String url = UriComponentsBuilder
                .fromHttpUrl(serpApiBaseURL)
                .queryParam("engine", "google_flights")
                .queryParam("departure_id", from)
                .queryParam("arrival_id", to)
                .queryParam("outbound_date", date)
                .queryParam("type", 2)
                .queryParam("currency", "INR")
                .queryParam("hl", "en")
                .queryParam("api_key", serpApiKey)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            return mapFlights(root);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to parse flight response", e);
        }
    }

    private List<FlightOffer> mapFlights(JsonNode root) {
        List<FlightOffer> offers = new ArrayList<>();
        extractFlights(root.path("best_flights"), offers);
        extractFlights(root.path("other_flights"), offers);
        return offers;
    }

    private void extractFlights(JsonNode flightArray, List<FlightOffer> offers) {

        if (!flightArray.isArray()) {
            return;
        }

        for (JsonNode flight : flightArray) {
            FlightOffer offer = new FlightOffer();
            JsonNode legs = flight.path("flights");
            JsonNode firstLeg = legs.get(0);
            JsonNode lastLeg = legs.get(legs.size() - 1);

            offer.setAirline(firstLeg.path("airline").asText());
            offer.setFlightNumber(firstLeg.path("flight_number").asText());
            offer.setDepartureAirport(firstLeg.path("departure_airport").path("id").asText());
            offer.setArrivalAirport(lastLeg.path("arrival_airport").path("id").asText());
            offer.setDepartureTime(firstLeg.path("departure_airport").path("time").asText());
            offer.setArrivalTime(lastLeg.path("arrival_airport").path("time").asText());
            offer.setDuration(flight.path("total_duration").asInt());
            offer.setStops(flight.path("layovers").size());
            offer.setPrice(flight.path("price").asDouble());
            offer.setCurrency("INR");
            offers.add(offer);
        }
    }
}