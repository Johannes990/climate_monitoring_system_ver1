package com.climate_monitoring_system.domain.climatedata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "SensorReading", schema = "climatedata")
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SensorReadingId")
    private long sensorReadingId;

    @Column(name = "Temperature")
    private float temperature;

    @Column(name = "RelHumidity")
    private float relHumidity;

    @Column(name = "ReadingTime")
    private Timestamp readingTime;

    @ManyToOne
    @JoinColumn(name = "SensorId", nullable = false)
    private Sensor sensor;
}
