package com.climate_monitoring_system.domain.climatedata;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ControlParameterSet", schema = "climatedata")
public class ControlParameterSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ControlParameterSetId")
    private long controlParameterSetId;

    @Column(name = "TempNorm")
    @NotNull
    private float tempNorm;

    @Column(name = "TempTolerance")
    @NotNull
    private float tempTolerance;

    @Column(name = "RelHumidityNorm")
    @NotNull
    private float relHumidityNorm;

    @Column(name = "RelHumidityTolerance")
    @NotNull
    private float relHumidityTolerance;
}
