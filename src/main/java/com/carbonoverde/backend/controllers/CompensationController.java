package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.configs.security.SecurityRules;
import com.carbonoverde.backend.dtos.requests.CompensationRequestDto;
import com.carbonoverde.backend.dtos.responses.CompensationResponseDto;
import com.carbonoverde.backend.services.CompensationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compensations")
@RequiredArgsConstructor
public class CompensationController {

    private final CompensationService compensationService;
    private final SecurityRules securityRules;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CompensationResponseDto> createCompensation(
            @Valid @RequestBody CompensationRequestDto compensationRequest) {
        CompensationResponseDto createdCompensation = compensationService.createCompensation(compensationRequest);
        return new ResponseEntity<>(createdCompensation, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<CompensationResponseDto>> getAllCompensations(Authentication authentication) {
        List<CompensationResponseDto> compensations;

        if (securityRules.isManager(authentication)) {
            compensations = compensationService.findAll();
        } else {
            // ADMIN: apenas compensações de empresas da mesma cidade
            String userCity = securityRules.getCurrentUserCity(authentication);
            compensations = compensationService.findByUserCity(userCity);
        }

        return ResponseEntity.ok(compensations);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<CompensationResponseDto> getCompensationById(@PathVariable Long id) {
        CompensationResponseDto compensation = compensationService.findById(id);
        return ResponseEntity.ok(compensation);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<List<CompensationResponseDto>> getCompensationsByCompanyId(@PathVariable Long companyId) {
        List<CompensationResponseDto> compensations = compensationService.findByCompanyId(companyId);
        return ResponseEntity.ok(compensations);
    }

    @GetMapping("/green-area/{greenAreaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<List<CompensationResponseDto>> getCompensationsByGreenAreaId(@PathVariable Long greenAreaId) {
        List<CompensationResponseDto> compensations = compensationService.findByGreenAreaId(greenAreaId);
        return ResponseEntity.ok(compensations);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CompensationResponseDto> updateCompensation(
            @PathVariable Long id,
            @Valid @RequestBody CompensationRequestDto compensationRequest) {
        CompensationResponseDto updatedCompensation = compensationService.updateCompensation(id, compensationRequest);
        return ResponseEntity.ok(updatedCompensation);
    }

    @PostMapping("/{compensationId}/emissions/{emissionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CompensationResponseDto> addEmissionToCompensation(
            @PathVariable Long compensationId,
            @PathVariable Long emissionId) {
        CompensationResponseDto updatedCompensation = compensationService.addEmissionToCompensation(compensationId, emissionId);
        return ResponseEntity.ok(updatedCompensation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCompensation(@PathVariable Long id) {
        compensationService.deleteCompensation(id);
        return ResponseEntity.noContent().build();
    }
}
