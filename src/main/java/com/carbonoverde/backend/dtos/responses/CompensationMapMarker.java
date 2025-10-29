package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompensationMapMarker {
    private Long id;
    private String companyName;
    private String greenAreaName;
    private Double latitude;
    private Double longitude;
    private Double co2Compensated;
    private String status;
}
