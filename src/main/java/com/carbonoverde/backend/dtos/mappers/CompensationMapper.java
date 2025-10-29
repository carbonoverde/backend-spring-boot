package com.carbonoverde.backend.dtos.mappers;

import com.carbonoverde.backend.dtos.requests.CompensationRequestDto;
import com.carbonoverde.backend.dtos.responses.CompensationResponseDto;
import com.carbonoverde.backend.entities.Compensation;
import com.carbonoverde.backend.entities.Company;
import com.carbonoverde.backend.entities.GreenArea;
import com.carbonoverde.backend.entities.MonthlyEmission;
import com.carbonoverde.backend.repositories.CompanyRepository;
import com.carbonoverde.backend.repositories.GreenAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompensationMapper {

    private final CompanyRepository companyRepository;
    private final GreenAreaRepository greenAreaRepository;

    public Compensation toEntity(CompensationRequestDto dto) {
        Company company = companyRepository.getReferenceById(dto.getCompanyId());
        GreenArea greenArea = greenAreaRepository.getReferenceById(dto.getGreenAreaId());

        Compensation compensation = new Compensation();
        compensation.setStatusCompensation(dto.getStatusCompensation());
        compensation.setAreaCompensated_M2(dto.getAreaCompensated_M2());
        compensation.setCo2CompensatedTons(dto.getCo2CompensatedTons());
        compensation.setInvestedValue(dto.getInvestedValue());
        compensation.setObservations(dto.getObservations());
        compensation.setCompany(company);
        compensation.setGreenArea(greenArea);

        return compensation;
    }

    public CompensationResponseDto toDto(Compensation entity) {
        return CompensationResponseDto.builder()
                .id(entity.getId())
                .statusCompensation(entity.getStatusCompensation())
                .areaCompensated_M2(entity.getAreaCompensated_M2())
                .co2CompensatedTons(entity.getCo2CompensatedTons())
                .investedValue(entity.getInvestedValue())
                .compensationDate(entity.getCompensationDate())
                .observations(entity.getObservations())
                .companyId(entity.getCompany().getId())
                .greenAreaId(entity.getGreenArea().getId())
                .emissionsCompensadasIds(entity.getEmissionsCompensadas()
                        .stream()
                        .map(MonthlyEmission::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
