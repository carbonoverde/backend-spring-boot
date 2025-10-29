package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyMapMarker {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double emissions;
    private String status; // "high", "medium", "low"
}
