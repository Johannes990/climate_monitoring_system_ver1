package com.climate_monitoring_system.controller.notification;

import com.climate_monitoring_system.domain.notification.NotificationType;
import com.climate_monitoring_system.dto.notification.NotificationDTO;
import com.climate_monitoring_system.service.notification.NotificationService;
import com.climate_monitoring_system.service.notification.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.climate_monitoring_system.controller.Paths.NOTIFICATIONS_ALL_QUERY_PATH;
import static com.climate_monitoring_system.controller.Paths.NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationTypeService notificationTypeService;

    @GetMapping(NOTIFICATIONS_ALL_QUERY_PATH)
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotificationDTOs();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH + "{typeId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationById(@PathVariable int typeId) {
        NotificationType notificationType = notificationTypeService.getNotificationTypeById(typeId);
        List<NotificationDTO> notifications = notificationService.getAllNotificationDTOsByNotificationType(notificationType);
        return ResponseEntity.ok(notifications);
    }
}
