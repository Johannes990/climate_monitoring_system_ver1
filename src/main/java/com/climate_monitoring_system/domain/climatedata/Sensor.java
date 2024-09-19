package com.climate_monitoring_system.domain.climatedata;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Sensor", schema = "climatedata")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SensorId")
    private long sensorId;

    @Column(name = "PassKey")
    @NotNull
    private String passKey;

    @Column(name = "DeviceCode")
    @NotNull
    private String deviceCode;

    @Column(name = "TempUnit")
    @NotNull
    private String tempUnit;

    @ManyToOne
    @JoinColumn(name = "LocationId")
    private Location location;
}
