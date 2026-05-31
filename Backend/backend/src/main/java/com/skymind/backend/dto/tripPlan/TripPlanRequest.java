package com.skymind.backend.dto.tripPlan;

import lombok.Data;

@Data
public class TripPlanRequest {

    private String source;
    private String destination;
    private Integer budget;
    private Integer days;
    private String travellerType;
}