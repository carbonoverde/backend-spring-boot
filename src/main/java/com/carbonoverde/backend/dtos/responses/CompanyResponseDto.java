package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponseDto
{
    private Long id;
    private String name;
    private String cnpj;
    private String dateRegistration;
    private Double accumulatedCo2;
    private Double monthlyEnergyConsumption;
    private Double monthlyWaterConsumption;
    private Double monthlyWaste;
    private AddressResponseDto address;
}
