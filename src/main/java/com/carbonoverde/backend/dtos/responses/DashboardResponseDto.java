package com.carbonoverde.backend.dtos.responses;


import lombok.Builder;
import lombok.Data;
import org.hibernate.stat.Statistics;

import java.util.List;

@Data
@Builder
public class DashboardResponseDto {
    private Statistics statistics;
    private MapData mapData;
    private List<RecentActivity> recentActivities;
    private EnvironmentalImpact environmentalImpact;
}
