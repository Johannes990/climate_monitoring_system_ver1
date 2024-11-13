package com.climate_monitoring_system.controller.notification;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.notification.NotificationType;
import com.climate_monitoring_system.dto.notification.NotificationDTO;
import com.climate_monitoring_system.service.climatedata.SensorService;
import com.climate_monitoring_system.service.notification.NotificationService;
import com.climate_monitoring_system.service.notification.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static com.climate_monitoring_system.controller.Paths.*;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationTypeService notificationTypeService;
    private final SensorService sensorService;

    @GetMapping(NOTIFICATIONS_ALL_QUERY_PATH)
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotificationDTOs();
        System.out.println("got notifications\n" + notifications);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_ACTIVE_QUERY_PATH)
    public ResponseEntity<List<NotificationDTO>> getActiveNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllActiveNotificationDTOs();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH + "{typeId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationById(@PathVariable int typeId) {
        NotificationType notificationType = notificationTypeService.getNotificationTypeById(typeId);
        List<NotificationDTO> notifications = notificationService
                .getAllNotificationDTOsByNotificationType(notificationType);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_BY_SENSOR_QUERY_PATH + "{sensorId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsBySensorId(@PathVariable int sensorId) {
        Sensor sensor = sensorService.getSensorById(sensorId);
        List<NotificationDTO> notifications = notificationService.getAllNotificationDTOsBySensor(sensor);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_BY_ACTION_TAKEN_QUERY_PATH + "{actionTaken}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByActionTaken(@PathVariable boolean actionTaken) {
        List<NotificationDTO> notifications = notificationService
                .getAllNotificationDTOsWithActionTaken(actionTaken);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_BY_CONDITIONS_RESOLVED_QUERY_PATH + "{conditionsResolved}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByConditionsResolved(
            @PathVariable boolean conditionsResolved
    ) {
        List<NotificationDTO> notifications = notificationService
                .getAllNotificationDTOsWithConditionsSelfResolved(conditionsResolved);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_BEFORE_QUERY_PATH + "{timestamp}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsBefore(@PathVariable Timestamp timestamp) {
        List<NotificationDTO> notifications = notificationService.getAllNotificationsDTOsBefore(timestamp);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_AFTER_QUERY_PATH + "{timestamp}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsAfter(@PathVariable Timestamp timestamp) {
        List<NotificationDTO> notifications = notificationService.getAllNotificationsDTOsAfter(timestamp);
        return ResponseEntity.ok(notifications);
    }

}
