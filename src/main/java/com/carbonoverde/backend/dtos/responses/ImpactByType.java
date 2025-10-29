package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImpactByType {
    private Double reforestationImpact;
    private Double urbanGreenAreasImpact;
    private Double conservationImpact;
}
