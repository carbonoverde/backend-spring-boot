package com.carbonoverde.backend.dtos.mappers;

import com.carbonoverde.backend.dtos.requests.MonthlyEmissionRequestDto;
import com.carbonoverde.backend.dtos.responses.MonthlyEmissionResponseDto;
import com.carbonoverde.backend.entities.MonthlyEmission;
import com.carbonoverde.backend.entities.Company;
import com.carbonoverde.backend.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlyEmissionMapper {

    private final CompanyRepository companyRepository;

    public MonthlyEmission toEntity(MonthlyEmissionRequestDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + dto.getCompanyId()));

        MonthlyEmission emission = new MonthlyEmission();
        emission.setCompany(company);
        emission.setMonthReference(dto.getMonthReference());
        emission.setEmissionsCo2(dto.getEmissionsCo2());
        emission.setConsumptionEnergy(dto.getConsumptionEnergy());
        emission.setConsumptionWater(dto.getConsumptionWater());
        emission.setGeneratedWaste(dto.getGeneratedWaste());
        return emission;
    }

    public MonthlyEmissionResponseDto toDto(MonthlyEmission entity) {
        return MonthlyEmissionResponseDto.builder()
                .id(entity.getId())
                .companyId(entity.getCompany().getId())
                .monthReference(entity.getMonthReference())
                .emissionsCo2(entity.getEmissionsCo2())
                .consumptionEnergy(entity.getConsumptionEnergy())
                .consumptionWater(entity.getConsumptionWater())
                .generatedWaste(entity.getGeneratedWaste())
                .dateRegister(entity.getDateRegister())
                .alreadyCompensated(entity.getAlreadyCompensated())
                .build();
    }
}
