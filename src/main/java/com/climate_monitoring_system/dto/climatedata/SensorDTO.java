package com.climate_monitoring_system.dto.climatedata;

import lombok.Data;

@Data
public class SensorDTO {
    private long sensorId;
    private String passKey;
    private String deviceCode;
    private String tempUnit;
    private LocationDTO location;
}
