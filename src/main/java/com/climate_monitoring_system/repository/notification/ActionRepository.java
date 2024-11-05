package com.climate_monitoring_system.repository.notification;

import com.climate_monitoring_system.domain.notification.Action;
import com.climate_monitoring_system.domain.userauth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    List<Action> findAllByUser(AppUser user);
    List<Action> findAllByTimestampAfter(Timestamp timestamp);
    List<Action> findAllByTimestampBefore(Timestamp timestamp);
}
