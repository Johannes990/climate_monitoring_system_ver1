package com.climate_monitoring_system.dto.climatedata;

import lombok.Data;

@Data
public class LocationDTO {
    private long locationId;
    private String locationDescription;
    private ControlParameterSetDTO controlParameterSet;
}
