package com.skymind.backend.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPreferenceProfile {

    private int morningWeight;

    private int airlineWeight;

    private int comfortWeight;

    private int cheapestWeight;
}
