package com.skymind.backend.dto;

import lombok.Data;

@Data
public class AiRecommendationResponse {

    private String recommendedAirline;

    private Double price;

    private String duration;

    private Integer stops;

    private String reason;
}