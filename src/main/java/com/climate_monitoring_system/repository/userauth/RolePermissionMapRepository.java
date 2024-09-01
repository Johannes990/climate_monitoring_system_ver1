package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.RolePermissionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMapRepository extends JpaRepository<RolePermissionMap, Integer> {

}
