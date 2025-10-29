package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.configs.security.SecurityRules;
import com.carbonoverde.backend.dtos.requests.GreenAreaRequestDto;
import com.carbonoverde.backend.dtos.responses.GreenAreaResponseDto;
import com.carbonoverde.backend.services.GreenAreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/green-areas")
@RequiredArgsConstructor
public class GreenAreaController {

    private final GreenAreaService greenAreaService;
    private final SecurityRules securityRules;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<GreenAreaResponseDto> createGreenArea(@Valid @RequestBody GreenAreaRequestDto greenAreaRequest) {
        GreenAreaResponseDto createdGreenArea = greenAreaService.createGreenArea(greenAreaRequest);
        return new ResponseEntity<>(createdGreenArea, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<GreenAreaResponseDto>> getAllGreenAreas(Authentication authentication) {
        List<GreenAreaResponseDto> greenAreas;

        if (securityRules.isManager(authentication)) {
            greenAreas = greenAreaService.findAll();
        } else if (securityRules.isAdmin(authentication)) {
            // ADMIN: apenas áreas verdes da mesma cidade
            String userCity = securityRules.getCurrentUserCity(authentication);
            greenAreas = greenAreaService.findByCity(userCity);
        } else {
            // USER: apenas para visualização no dashboard
            greenAreas = greenAreaService.findAllForDashboard();
        }

        return ResponseEntity.ok(greenAreas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<GreenAreaResponseDto> getGreenAreaById(@PathVariable Long id) {
        GreenAreaResponseDto greenArea = greenAreaService.findById(id);
        return ResponseEntity.ok(greenArea);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<GreenAreaResponseDto> updateGreenArea(
            @PathVariable Long id,
            @Valid @RequestBody GreenAreaRequestDto greenAreaRequest) {
        GreenAreaResponseDto updatedGreenArea = greenAreaService.updateGreenArea(id, greenAreaRequest);
        return ResponseEntity.ok(updatedGreenArea);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteGreenArea(@PathVariable Long id) {
        greenAreaService.deleteGreenArea(id);
        return ResponseEntity.noContent().build();
    }
}
