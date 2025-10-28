package com.carbonoverde.backend.dtos.requests;

import com.carbonoverde.backend.enums.StatusCompensation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CompensationRequestDto
{
    @NotNull(message = "Status da compensação é obrigatório")
    private StatusCompensation statusCompensation;

    @Positive(message = "Área compensada deve ser um valor positivo")
    private Double areaCompensated_M2;

    @Positive(message = "CO2 compensado deve ser um valor positivo")
    private Double co2CompensatedTons;

    @Positive(message = "Valor investido deve ser um valor positivo")
    private Double investedValue;

    @NotNull(message = "ID da empresa é obrigatório")
    private Long companyId;

    @NotNull(message = "ID da área verde é obrigatório")
    private Long greenAreaId;

    private String observations;
}
