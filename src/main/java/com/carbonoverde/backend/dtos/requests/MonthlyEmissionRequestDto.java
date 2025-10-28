package com.carbonoverde.backend.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.YearMonth;

@Data
public class MonthlyEmissionRequestDto
{
    @NotNull(message = "ID da empresa é obrigatório")
    private Long companyId;

    @NotNull(message = "Mês de referência é obrigatório")
    private YearMonth monthReference;

    @Positive(message = "Emissões de CO2 devem ser um valor positivo")
    private Double emissionsCo2;

    @Positive(message = "Consumo de energia deve ser um valor positivo")
    private Double consumptionEnergy;

    @Positive(message = "Consumo de água deve ser um valor positivo")
    private Double consumptionWater;

    @Positive(message = "Resíduos gerados devem ser um valor positivo")
    private Double generatedWaste;
}
