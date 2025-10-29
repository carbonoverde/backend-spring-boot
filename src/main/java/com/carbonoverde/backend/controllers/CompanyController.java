package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.dtos.requests.CompanyRequestDto;
import com.carbonoverde.backend.dtos.responses.CompanyResponseDto;
import com.carbonoverde.backend.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // Constantes para expressões que NÃO usam parâmetros de método
    private static final String MANAGER_ADMIN_USER = "hasAnyRole('MANAGER', 'ADMIN', 'USER')";
    private static final String MANAGER_ONLY = "hasRole('MANAGER')";

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or (hasRole('ADMIN') and @securityRules.isUserInSameCity(authentication, #companyRequest.address.city))")
    public ResponseEntity<CompanyResponseDto> createCompany(@Valid @RequestBody CompanyRequestDto companyRequest) {
        CompanyResponseDto createdCompany = companyService.createCompany(companyRequest);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(MANAGER_ADMIN_USER)
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> companies = companyService.findAll();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or (hasRole('ADMIN') and @userSecurity.canAccessCompany(authentication, #id))")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id) {
        CompanyResponseDto company = companyService.findById(id);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or (hasRole('ADMIN') and @userSecurity.canAccessCompany(authentication, #id))")
    public ResponseEntity<CompanyResponseDto> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDto companyRequest) {
        CompanyResponseDto updatedCompany = companyService.updateCompany(id, companyRequest);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(MANAGER_ONLY)
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
