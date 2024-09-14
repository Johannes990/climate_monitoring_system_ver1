package com.climate_monitoring_system.dto.climatedata;

import lombok.Data;

@Data
public class SensorReadingDTO {
    private long sensorReadingId;
    private float temperature;
    private float relHumidity;
    private SensorDTO sensor;
}
