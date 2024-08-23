package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
    
}
