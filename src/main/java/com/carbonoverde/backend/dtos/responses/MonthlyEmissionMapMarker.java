package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;
import java.time.YearMonth;

@Data
@Builder
public class MonthlyEmissionMapMarker {
    private Long id;
    private String companyName;
    private Double latitude;
    private Double longitude;
    private Double emissions;
    private YearMonth monthReference;
    private String status; // "high", "medium", "low"
}
