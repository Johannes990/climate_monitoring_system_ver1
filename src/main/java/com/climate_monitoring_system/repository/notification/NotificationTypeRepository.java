package com.climate_monitoring_system.repository.notification;

import com.climate_monitoring_system.domain.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    Optional<NotificationType> findByNotificationTypeDescription(String notificationTypeDescription);
}
