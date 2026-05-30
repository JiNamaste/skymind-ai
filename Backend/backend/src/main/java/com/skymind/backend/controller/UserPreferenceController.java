package com.skymind.backend.controller;

import com.skymind.backend.dto.UserPreference;
import com.skymind.backend.service.UserPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
@Tag(name = "Preferences", description = "User Preference APIs")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @Operation(summary = "Save user travel preference")
    @PostMapping
    public ResponseEntity<UserPreference> savePreference(@RequestBody UserPreference preference) {
        userPreferenceService.savePreference(preference);
        return ResponseEntity.ok(preference);
    }


    @Operation(summary = "Get saved user preference")
    @GetMapping
    public ResponseEntity<UserPreference> getPreference() {
        UserPreference preference = userPreferenceService.getPreference();
        if (preference == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(preference);
    }
}
