package com.climate_monitoring_system.dto.notification;

import com.climate_monitoring_system.dto.climatedata.SensorDTO;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDTO {
    private long notificationId;
    private NotificationTypeDTO notificationType;
    private SensorDTO sensor;
    private Timestamp timestamp;
    private boolean conditionsSelfResolved;
    private boolean userActionTaken;
    private ActionDTO action;
    private boolean isActive;
}
