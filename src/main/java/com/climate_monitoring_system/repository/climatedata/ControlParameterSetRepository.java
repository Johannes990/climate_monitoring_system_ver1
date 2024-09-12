package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControlParameterSetRepository extends JpaRepository<ControlParameterSet, Long> {
}
