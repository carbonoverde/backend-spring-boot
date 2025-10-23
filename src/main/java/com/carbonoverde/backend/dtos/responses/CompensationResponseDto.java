package com.carbonoverde.backend.dtos.responses;

import com.carbonoverde.backend.enums.StatusCompensation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CompensationResponseDto
{
    private Long id;
    private StatusCompensation statusCompensation;
    private Double areaCompensated_M2;
    private Double co2CompensatedTons;
    private Double investedValue;
    private LocalDateTime compensationDate;
    private String observations;
    private Long companyId;
    private Long greenAreaId;
    private List<Long> emissionsCompensadasIds;
}
