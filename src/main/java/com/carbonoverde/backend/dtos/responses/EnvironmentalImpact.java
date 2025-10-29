package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvironmentalImpact {
    // Equivalentes ambientais (dados educativos)
    private Double equivalentCarsRemoved;
    private Double equivalentTreesPlanted;
    private Double equivalentForestArea; // em hectares
    private Double equivalentEnergySaved; // em MWh

    // Impacto por tipo
    private ImpactByType impactByType;
}
