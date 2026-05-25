package com.skymind.backend.service;


import com.skymind.backend.dto.UserPreferenceProfile;
import org.springframework.stereotype.Service;

@Service
public class PreferenceParserService {

    public UserPreferenceProfile parse(String preference) {
        int morning = 10;
        int airline = 10;
        int comfort = 10;
        int cheapest = 10;

        if (preference == null) {
            return defaultProfile();
        }

        String input = preference.toLowerCase();

        if (input.contains("morning")) {
            morning += 30;
        }

        if (input.contains("cheap") || input.contains("cheapest")) {
            cheapest += 40;
        }

        if (input.contains("comfort")) {
            comfort += 30;
        }

        if (input.contains("indigo") || input.contains("vistara")) {
            airline += 30;
        }

        return UserPreferenceProfile
                .builder()
                .morningWeight(morning)
                .airlineWeight(airline)
                .comfortWeight(comfort)
                .cheapestWeight(cheapest)
                .build();
    }

    private UserPreferenceProfile defaultProfile() {

        return UserPreferenceProfile.builder()
                .morningWeight(10)
                .airlineWeight(10)
                .comfortWeight(10)
                .cheapestWeight(10)
                .build();
    }
}