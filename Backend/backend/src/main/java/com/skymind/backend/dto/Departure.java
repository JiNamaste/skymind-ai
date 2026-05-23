package com.skymind.backend.dto;

import lombok.Data;

@Data
public class Departure {

    private String airport;
    private String iata;
    private String scheduled;
}
