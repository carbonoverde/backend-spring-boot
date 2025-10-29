package com.carbonoverde.backend.services;


import com.carbonoverde.backend.dtos.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CompanyService companyService;
    private final GreenAreaService greenAreaService;
    private final CompensationService compensationService;
    private final MonthlyEmissionService monthlyEmissionService;

    public DashboardResponseDto getDashboardData() {
        return DashboardResponseDto.builder()
                .statistics((org.hibernate.stat.Statistics) getStatistics())
                .mapData(getMapDataInternal()) // ⬅️ MUDAR NOME AQUI
                .recentActivities(getRecentActivities())
                .environmentalImpact(getEnvironmentalImpact())
                .build();
    }

    private MapData getMapDataInternal() {
        return MapData.builder()
                .companies(companyService.findAllForMap())
                .greenAreas(greenAreaService.findAllForMap())
                .compensations(compensationService.findAllForMap())
                .build();
    }

    public Object getMapDataForEndpoint() {
        return getMapDataInternal();
    }

    private Statistics getStatistics() {
        Double totalCO2Compensated = compensationService.getTotalCO2Compensated();
        Double totalCO2Emitted = monthlyEmissionService.getTotalCO2Emitted();
        Double totalInvested = compensationService.getTotalInvested();

        return Statistics.builder()
                .totalCompanies(companyService.countAll())
                .totalGreenAreas(greenAreaService.countAll())
                .totalCompensations(compensationService.countAll())
                .totalMonthlyEmissions(monthlyEmissionService.countAll())
                .totalInvested(totalInvested)
                .averageInvestment(compensationService.countAll() > 0 ?
                        totalInvested / compensationService.countAll() : 0.0)
                .totalCO2Compensated(totalCO2Compensated)
                .totalCO2Emitted(totalCO2Emitted)
                .totalAreaCompensated(compensationService.getTotalAreaCompensated())
                .monthlyCO2Reduction(calculateMonthlyReduction())
                .compensationGrowthRate(calculateGrowthRate())
                .build();
    }

    private List<RecentActivity> getRecentActivities() {
        return compensationService.findRecentActivities();
    }

    private EnvironmentalImpact getEnvironmentalImpact() {
        Double totalCO2Compensated = compensationService.getTotalCO2Compensated();

        return EnvironmentalImpact.builder()
                .equivalentCarsRemoved(calculateEquivalentCars(totalCO2Compensated))
                .equivalentTreesPlanted(calculateEquivalentTrees(totalCO2Compensated))
                .equivalentForestArea(calculateEquivalentForest(totalCO2Compensated))
                .equivalentEnergySaved(calculateEquivalentEnergy(totalCO2Compensated))
                .impactByType(getImpactByType())
                .build();
    }

    // Métodos de cálculo para equivalentes ambientais
    private Double calculateEquivalentCars(Double co2Tons) {
        // 1 ton CO2 ≈ 0,42 carros retirados por ano (fonte aproximada)
        return co2Tons * 0.42;
    }

    private Double calculateEquivalentTrees(Double co2Tons) {
        // 1 árvore absorve ~22 kg CO2 por ano ≈ 45 árvores por tonelada
        return co2Tons * 45;
    }

    private Double calculateEquivalentForest(Double co2Tons) {
        // 1 hectare de floresta absorve ~6 ton CO2 por ano
        return co2Tons / 6;
    }

    private Double calculateEquivalentEnergy(Double co2Tons) {
        // 1 MWh ≈ 0.5 ton CO2 (depende da fonte)
        return co2Tons * 2;
    }

    private ImpactByType getImpactByType() {
        // Implementar cálculo por tipo de área verde
        return ImpactByType.builder()
                .reforestationImpact(40.0) // Exemplo: 40% do impacto vem de reflorestamento
                .urbanGreenAreasImpact(35.0)
                .conservationImpact(25.0)
                .build();
    }

    private Double calculateMonthlyReduction() {
        // Implementar lógica de cálculo de redução mensal
        return 5.2; // % de redução
    }

    private Double calculateGrowthRate() {
        // Implementar lógica de taxa de crescimento
        return 12.5; // % de crescimento
    }
}
