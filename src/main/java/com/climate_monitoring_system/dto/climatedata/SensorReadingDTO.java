package com.climate_monitoring_system.dto.climatedata;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SensorReadingDTO {
    private long sensorReadingId;
    private float temperature;
    private float relHumidity;
    private Timestamp readingTime;
    private SensorDTO sensor;
}
