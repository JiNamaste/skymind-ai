package com.skymind.backend.dto;

import lombok.Data;

@Data
public class Arrival {

    private String airport;
    private String iata;
    private String scheduled;
}