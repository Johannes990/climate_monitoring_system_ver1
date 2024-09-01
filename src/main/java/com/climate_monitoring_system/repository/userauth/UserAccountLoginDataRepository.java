package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.UserAccountLoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountLoginDataRepository extends JpaRepository<UserAccountLoginData, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<UserAccountLoginData> findByEmailIgnoreCase(String email);
}
