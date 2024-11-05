package com.climate_monitoring_system.repository.notification;

import com.climate_monitoring_system.domain.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
}
