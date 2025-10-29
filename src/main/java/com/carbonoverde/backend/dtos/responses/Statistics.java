package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Statistics {
    private Long totalCompanies;
    private Long totalGreenAreas;
    private Long totalCompensations;
    private Long totalMonthlyEmissions;

    // Métricas financeiras
    private Double totalInvested;
    private Double averageInvestment;

    // Métricas ambientais
    private Double totalCO2Compensated;
    private Double totalCO2Emitted;
    private Double totalAreaCompensated;

    // Tendências
    private Double monthlyCO2Reduction;
    private Double compensationGrowthRate;
}
