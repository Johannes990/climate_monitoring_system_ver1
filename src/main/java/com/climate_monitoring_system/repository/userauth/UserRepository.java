package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserName(String userName);
    Optional<AppUser> findByEmail(String email);
}
