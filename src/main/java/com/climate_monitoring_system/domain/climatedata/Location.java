package com.climate_monitoring_system.domain.climatedata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Location", schema = "climatedata")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationId")
    private long locationId;

    @Column(name = "LocationDescription")
    private String locationDescription;

    @ManyToOne
    @JoinColumn(name = "ControlParameterSetId")
    private ControlParameterSet controlParameterSet;
}
