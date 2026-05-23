package com.skymind.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class FlightSearchResponse {

    private List<FlightData> data;
}