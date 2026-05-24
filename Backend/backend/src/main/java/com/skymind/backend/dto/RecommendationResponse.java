package com.skymind.backend.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendationResponse {

    private FlightScore recommended;
    private List<FlightScore> top3;
}