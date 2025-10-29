package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.configs.security.SecurityRules;
import com.carbonoverde.backend.dtos.requests.MonthlyEmissionRequestDto;
import com.carbonoverde.backend.dtos.responses.MonthlyEmissionResponseDto;
import com.carbonoverde.backend.services.MonthlyEmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-emissions")
@RequiredArgsConstructor
public class MonthlyEmissionController {

    private final MonthlyEmissionService monthlyEmissionService;
    private final SecurityRules securityRules;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<MonthlyEmissionResponseDto> createMonthlyEmission(
            @Valid @RequestBody MonthlyEmissionRequestDto emissionRequest) {
        MonthlyEmissionResponseDto createdEmission = monthlyEmissionService.createMonthlyEmission(emissionRequest);
        return new ResponseEntity<>(createdEmission, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<MonthlyEmissionResponseDto>> getAllMonthlyEmissions(Authentication authentication) {
        List<MonthlyEmissionResponseDto> emissions;

        if (securityRules.isManager(authentication)) {
            emissions = monthlyEmissionService.findAll();
        } else {
            // ADMIN: apenas emissões de empresas da mesma cidade
            String userCity = securityRules.getCurrentUserCity(authentication);
            emissions = monthlyEmissionService.findByUserCity(userCity);
        }

        return ResponseEntity.ok(emissions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or (hasRole('ADMIN') and @userSecurity.canAccessMonthlyEmission(authentication, #id))")
    public ResponseEntity<MonthlyEmissionResponseDto> getMonthlyEmissionById(@PathVariable Long id) {
        MonthlyEmissionResponseDto emission = monthlyEmissionService.findById(id);
        return ResponseEntity.ok(emission);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('MANAGER') or (hasRole('ADMIN') and @userSecurity.canAccessCompany(authentication, #companyId))")
    public ResponseEntity<List<MonthlyEmissionResponseDto>> getEmissionsByCompanyId(@PathVariable Long companyId) {
        List<MonthlyEmissionResponseDto> emissions = monthlyEmissionService.findByCompanyId(companyId);
        return ResponseEntity.ok(emissions);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<MonthlyEmissionResponseDto> updateMonthlyEmission(
            @PathVariable Long id,
            @Valid @RequestBody MonthlyEmissionRequestDto emissionRequest) {
        MonthlyEmissionResponseDto updatedEmission = monthlyEmissionService.updateMonthlyEmission(id, emissionRequest);
        return ResponseEntity.ok(updatedEmission);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteMonthlyEmission(@PathVariable Long id) {
        monthlyEmissionService.deleteMonthlyEmission(id);
        return ResponseEntity.noContent().build();
    }
}
