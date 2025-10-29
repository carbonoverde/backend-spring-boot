package com.carbonoverde.backend.dtos.mappers;

import com.carbonoverde.backend.dtos.requests.CompanyRequestDto;
import com.carbonoverde.backend.dtos.responses.CompanyResponseDto;
import com.carbonoverde.backend.entities.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final AddressMapper addressMapper;

    public Company toEntity(CompanyRequestDto dto) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setCnpj(dto.getCnpj());
        company.setMonthlyEnergyConsumption(dto.getMonthlyEnergyConsumption());
        company.setMonthlyWaterConsumption(dto.getMonthlyWaterConsumption());
        company.setMonthlyWaste(dto.getMonthlyWaste());
        company.setAddress(addressMapper.toEntity(dto.getAddress()));
        return company;
    }

    public CompanyResponseDto toDto(Company entity) {
        return CompanyResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cnpj(entity.getCnpj())
                .dateRegistration(entity.getDateRegistration().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .accumulatedCo2(entity.getAccumulatedCo2())
                .monthlyEnergyConsumption(entity.getMonthlyEnergyConsumption())
                .monthlyWaterConsumption(entity.getMonthlyWaterConsumption())
                .monthlyWaste(entity.getMonthlyWaste())
                .address(addressMapper.toDto(entity.getAddress()))
                .build();
    }
}
