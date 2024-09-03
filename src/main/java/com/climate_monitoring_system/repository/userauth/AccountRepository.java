package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountType(String accountType);
}
