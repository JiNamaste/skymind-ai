package com.skymind.backend.externalApi;

import com.skymind.backend.dto.FlightSearchResponse;
import com.skymind.backend.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AviationstackClient {

    @Value("${aviationstack.api.key}")
    private String apiKey;

    @Value("${aviationstack.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public AviationstackClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
}