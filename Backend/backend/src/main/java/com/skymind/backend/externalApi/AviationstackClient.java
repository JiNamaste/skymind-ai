package com.skymind.backend.externalApi;

import com.skymind.backend.dto.FlightOffer;
import com.skymind.backend.dto.FlightSearchResponse;
import com.skymind.backend.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AviationstackClient {

    @Value("${aviationstack.api.key}")
    private String apiKey;

    @Value("${aviationstack.base.url}")
    private String baseUrl;

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
//
//        String url = baseUrl + "/flights" + "?access_key=" + apiKey + "&dep_iata=" + from + "&arr_iata=" + to + "&flight_date=" + date;
//        String response = restTemplate.getForObject(url, String.class);

        List<FlightOffer> flights = new ArrayList<>();

        flights.add(
                FlightOffer.builder()
                        .airline("IndiGo")
                        .flightNumber("6E-2134")
                        .departureAirport(from)
                        .arrivalAirport(to)
                        .departureTime(date + "T08:00")
                        .arrivalTime(date + "T10:30")
                        .duration(150)
                        .stops(0)
                        .price(5200.0)
                        .currency("INR")
                        .build()
        );

        flights.add(
                FlightOffer.builder()
                        .airline("Air India")
                        .flightNumber("AI-507")
                        .departureAirport(from)
                        .arrivalAirport(to)
                        .departureTime(date + "T09:30")
                        .arrivalTime(date + "T12:45")
                        .duration(195)
                        .stops(1)
                        .price(4700.0)
                        .currency("INR")
                        .build()
        );

        return flights;
        //return mapResponse(response);
    }

    private List<FlightOffer> mapResponse(String response) {

        List<FlightOffer> flights = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode data = root.get("data");

            if (data != null && data.isArray()) {

                for (JsonNode node : data) {

                    FlightOffer offer =
                            FlightOffer.builder()
                                    .airline(node.path("airline").path("name").asText())
                                    .flightNumber(node.path("flight").path("iata").asText())
                                    .departureAirport(node.path("departure").path("iata").asText())
                                    .arrivalAirport(node.path("arrival").path("iata").asText())
                                    .departureTime(node.path("departure").path("scheduled").asText())
                                    .arrivalTime(node.path("arrival").path("scheduled").asText())
                                    .duration(0)
                                    .stops(0)
                                    .price(0.0)
                                    .currency("INR")
                                    .build();

                    flights.add(offer);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing AviationStack response", e);
        }

        return flights;
    }
}