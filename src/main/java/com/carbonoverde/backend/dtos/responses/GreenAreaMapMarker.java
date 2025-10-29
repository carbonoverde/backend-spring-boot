package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreenAreaMapMarker {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double areaAvailable;
    private String type;
    private String status;
}
