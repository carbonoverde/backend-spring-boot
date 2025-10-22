package com.carbonoverde.backend.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRequestDto
{
    @NotBlank(message = "Nome da empresa é obrigatório")
    @Size(min = 2, max = 100, message = "Nome da empresa deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "CNPJ deve ter exatamente 14 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "CNPJ deve conter apenas números")
    private String cnpj;

    @NotNull(message = "Endereço é obrigatório")
    private AddressRequestDto address;

    private Double monthlyEnergyConsumption;
    private Double monthlyWaterConsumption;
    private Double monthlyWaste;
}