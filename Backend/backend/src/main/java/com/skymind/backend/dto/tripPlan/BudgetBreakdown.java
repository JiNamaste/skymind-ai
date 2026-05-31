package com.skymind.backend.dto.tripPlan;

import lombok.Data;

@Data
public  class BudgetBreakdown {
    private Integer flight;
    private Integer hotel;
    private Integer food;
    private Integer localTravel;
    private Integer activities;
}