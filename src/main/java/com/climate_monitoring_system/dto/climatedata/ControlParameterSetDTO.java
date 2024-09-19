package com.climate_monitoring_system.dto.climatedata;

import lombok.Data;

@Data
public class ControlParameterSetDTO {
    private long controlParameterSetId;
    private float tempNorm;
    private float tempTolerance;
    private float relHumidityNorm;
    private float relHumidityTolerance;
}
