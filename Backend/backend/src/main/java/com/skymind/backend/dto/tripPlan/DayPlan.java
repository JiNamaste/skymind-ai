package com.skymind.backend.dto.tripPlan;

import lombok.Data;

import java.util.List;

@Data
public  class DayPlan {
    private Integer day;
    private String title;
    private List<String> activities;
    private String estimatedCost;
}
