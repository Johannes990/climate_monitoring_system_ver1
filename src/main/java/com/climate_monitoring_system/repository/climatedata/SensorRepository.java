package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.Location;
import com.climate_monitoring_system.domain.climatedata.Sensor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByPassKey(String passKey);
    List<Sensor> findAllByLocation(Location location);
    List<Sensor> findAllByDeviceCode(String deviceCode);
    List<Sensor> findAllByLocationAndDeviceCode(Location location, @NotNull String deviceCode);
}
