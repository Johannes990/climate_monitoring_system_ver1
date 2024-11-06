package com.climate_monitoring_system.controller.notification;

import com.climate_monitoring_system.dto.notification.NotificationTypeDTO;
import com.climate_monitoring_system.service.notification.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationTypeController {
    private final NotificationTypeService notificationTypeService;

    @GetMapping("/notificationtypes/{id}")
    public ResponseEntity<NotificationTypeDTO> getNotificationTypeById(@PathVariable int id) {
        NotificationTypeDTO notificationTypeDTO = notificationTypeService.getNotificationTypeDTOById(id);

        return ResponseEntity.ok(notificationTypeDTO);
    }

    @GetMapping("/notificationtypes/all")
    public ResponseEntity<List<NotificationTypeDTO>> getAllNotificationTypes() {
        List<NotificationTypeDTO> allNotificationTypes = notificationTypeService.getAllNotificationTypeDTOs();

        return ResponseEntity.ok(allNotificationTypes);
    }

    @PostMapping("/notificationtypes/add")
    public ResponseEntity<String> addNotificationType(@RequestBody NotificationTypeDTO notificationTypeDTO) {
        boolean notificationTypeSaved = notificationTypeService.addNotificationType(notificationTypeDTO);

        if (notificationTypeSaved) {
            return ResponseEntity.ok("Notification type posted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to post notification type");
    }

    @DeleteMapping("/notificationtypes/delete/{id}")
    public ResponseEntity<String> deleteNotificationType(@PathVariable int id) {
        boolean notificationDeleted = notificationTypeService.deleteNotificationType(id);

        if (notificationDeleted) {
            return ResponseEntity.ok("Notification type deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to delete notification type");
    }
}
