package com.climate_monitoring_system.repository.userauth;

import org.springframework.data.jpa.repository.JpaRepository;
import com.climate_monitoring_system.domain.userauth.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<UserRole, Integer> {
    
}
