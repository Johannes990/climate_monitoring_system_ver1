package com.climate_monitoring_system.service.notification;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.notification.Notification;
import com.climate_monitoring_system.domain.notification.NotificationType;
import com.climate_monitoring_system.dto.notification.NotificationDTO;
import com.climate_monitoring_system.repository.notification.NotificationRepository;
import com.climate_monitoring_system.service.climatedata.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SensorService sensorService;
    private final NotificationTypeService notificationTypeService;
    private final ActionService actionService;

    public List<NotificationDTO> getAllNotificationDTOs() {
        List<Notification> allNotifications = notificationRepository.findAll();

        return notificationsToNotificationDTOs(allNotifications);
    }

    public List<NotificationDTO> getAllNotificationDTOsByNotificationType(
            NotificationType notificationType
    ) {
        List<Notification> allNotificationsByType = notificationRepository
                .findAllByNotificationType(notificationType);

        return notificationsToNotificationDTOs(allNotificationsByType);
    }

    public List<NotificationDTO> getAllNotificationDTOsBySensor(Sensor sensor) {
        List<Notification> allNotificationsBySensor = notificationRepository
                .findAllBySensor(sensor);

        return notificationsToNotificationDTOs(allNotificationsBySensor);
    }

    public List<NotificationDTO> getAllNotificationDTOsWithActionTaken(boolean actionTaken) {
        List<Notification> allNotificationsWithActionTaken = notificationRepository
                .findAllByUserActionTaken(actionTaken);

        return notificationsToNotificationDTOs(allNotificationsWithActionTaken);
    }

    public List<NotificationDTO> getAllNotificationDTOsWithConditionsSelfResolved(boolean selfResolved) {
        List<Notification> allNotificationsWithConditionsSelfResolved = notificationRepository
                .findAllByConditionsSelfResolved(selfResolved);

        return notificationsToNotificationDTOs(allNotificationsWithConditionsSelfResolved);
    }

    public List<NotificationDTO> getAllNotificationsDTOsBefore(Timestamp timestamp) {
        List<Notification> allNotificationsBefore = notificationRepository
                .findAllByTimeStampBefore(timestamp);

        return notificationsToNotificationDTOs(allNotificationsBefore);
    }

    public List<NotificationDTO> getAllNotificationsDTOsAfter(Timestamp timestamp) {
        List<Notification> allNotificationsAfter = notificationRepository
                .findAllByTimeStampAfter(timestamp);

        return notificationsToNotificationDTOs(allNotificationsAfter);
    }

    private List<NotificationDTO> notificationsToNotificationDTOs(
            List<Notification> notifications
    ) {
        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification notification : notifications) {
            notificationDTOs.add(notificationToNotificationDTO(notification));
        }

        return notificationDTOs;
    }

    private NotificationDTO notificationToNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(notification.getNotificationId());
        notificationDTO.setNotificationType(notificationTypeService
                .getNotificationTypeDTO(notification.getNotificationType()));
        notificationDTO.setSensor(sensorService.getSensorDTO(notification.getSensor()));
        notificationDTO.setTimestamp(notification.getTimeStamp());
        notificationDTO.setConditionsSelfResolved(notification.isConditionsSelfResolved());
        notificationDTO.setUserActionTaken(notification.isUserActionTaken());
        notificationDTO.setAction(actionService.actionToActionDTO(notification.getAction()));
        notificationDTO.setActive(notification.isActive());

        return notificationDTO;
    }
}
