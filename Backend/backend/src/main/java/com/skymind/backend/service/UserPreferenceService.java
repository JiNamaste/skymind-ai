package com.skymind.backend.service;

import com.skymind.backend.dto.UserPreference;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService {

    private UserPreference currentPreference;

    public void savePreference(UserPreference preference) {
        this.currentPreference = preference;
    }

    public UserPreference getPreference() {
        return currentPreference;
    }
}
