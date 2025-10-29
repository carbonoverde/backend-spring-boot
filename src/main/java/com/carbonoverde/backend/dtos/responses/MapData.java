package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MapData {
    private List<CompanyMapMarker> companies;
    private List<GreenAreaMapMarker> greenAreas;
    private List<CompensationMapMarker> compensations;
}
