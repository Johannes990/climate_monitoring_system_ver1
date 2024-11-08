package com.climate_monitoring_system.repository.notification;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.notification.Notification;
import com.climate_monitoring_system.domain.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByNotificationType(NotificationType notificationType);
    List<Notification> findAllBySensor(Sensor sensor);
    List<Notification> findAllByUserActionTaken(Boolean userActionTaken);
    List<Notification> findAllByConditionsSelfResolved(Boolean selfResolved);
    List<Notification> findAllByTimeStampAfter(Timestamp timeStamp);
    List<Notification> findAllByTimeStampBefore(Timestamp timeStamp);
}
