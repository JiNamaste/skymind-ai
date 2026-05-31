package com.skymind.backend.dto.tripPlan;


import lombok.Data;

import java.util.List;

@Data
public class TripPlanResponse {
private String destination;
private Integer days;
private String summary;
private BudgetBreakdown budgetBreakdown;
private List<DayPlan> itinerary;
private List<String> travelTips;
}