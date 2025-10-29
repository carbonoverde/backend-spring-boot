package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecentActivity {
    private Long id;
    private String type; // "COMPENSATION", "EMISSION", "GREEN_AREA", "COMPANY"
    private String description;
    private LocalDateTime timestamp;
    private String userName;
    private String companyName;
    private String status;
}
