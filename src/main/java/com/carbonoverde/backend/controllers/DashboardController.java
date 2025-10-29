package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.dtos.responses.DashboardResponseDto;
import com.carbonoverde.backend.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DashboardResponseDto> getDashboardData() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/map")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMapData() {
        Object mapData = dashboardService.getMapDataForEndpoint();
        return ResponseEntity.ok(mapData);
    }
}
