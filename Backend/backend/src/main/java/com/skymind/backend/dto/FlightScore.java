package com.skymind.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightScore {

    private FlightResultDto flight;
    private int score;
}