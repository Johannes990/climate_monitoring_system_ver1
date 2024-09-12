package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.domain.climatedata.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationDescription(String locationDescription);
    List<Location> findAllByControlParameterSet(ControlParameterSet controlParameterSet);
    List<Location> findAllByLocationDescriptionContaining(String description);
}
