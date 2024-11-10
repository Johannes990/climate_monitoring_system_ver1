package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.climatedata.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long>, JpaSpecificationExecutor<SensorReading> {
    List<SensorReading> findAllBySensor(Sensor sensor);
}
