package com.climate_monitoring_system.service.notification;

import com.climate_monitoring_system.domain.notification.NotificationType;
import com.climate_monitoring_system.dto.notification.NotificationTypeDTO;
import com.climate_monitoring_system.repository.notification.NotificationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationTypeService {
    private final NotificationTypeRepository notificationTypeRepository;

    public NotificationTypeDTO getNotificationTypeDTOById(long id) {
        Optional<NotificationType> notificationType = notificationTypeRepository.findById(id);

        return notificationType.map(this::getNotificationTypeDTO)
                .orElseGet(NotificationTypeDTO::new);
    }

    public List<NotificationTypeDTO> getAllNotificationTypeDTOs() {
        List<NotificationType> notificationTypes = notificationTypeRepository.findAll();
        List<NotificationTypeDTO> notificationTypeDTOs = new ArrayList<>();

        for (NotificationType notificationType: notificationTypes) {
            notificationTypeDTOs.add(getNotificationTypeDTO(notificationType));
        }

        return notificationTypeDTOs;
    }

    private NotificationTypeDTO getNotificationTypeDTO(NotificationType notificationType) {
        NotificationTypeDTO notificationTypeDTO = new NotificationTypeDTO();
        notificationTypeDTO.setNotificationTypeId(
                notificationType.getNotificationTypeId()
        );
        notificationTypeDTO.setNotificationTypeDescription(
                notificationType.getNotificationTypeDescription()
        );

        return notificationTypeDTO;
    }
}
