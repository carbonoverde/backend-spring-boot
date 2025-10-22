package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Data
@Builder
public class MonthlyEmissionResponseDto
{
    private Long id;
    private Long companyId;
    private YearMonth monthReference;
    private Double emissionsCo2;
    private Double consumptionEnergy;
    private Double consumptionWater;
    private Double generatedWaste;
    private LocalDateTime dateRegister;
    private Boolean alreadyCompensated;
}
