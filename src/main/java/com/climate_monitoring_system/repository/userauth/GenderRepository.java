package com.climate_monitoring_system.repository.userauth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.climate_monitoring_system.domain.userauth.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    
}
