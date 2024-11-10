package com.climate_monitoring_system.domain.notification;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Notification", schema = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationId")
    private long notificationId;

    @ManyToOne
    @JoinColumn(name = "NotificationTypeId", nullable = false,
                foreignKey = @ForeignKey(name = "FK_Notification_NotificationType"))
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "SensorId", nullable = false,
                foreignKey = @ForeignKey(name = "FK_Notification_SensorId"))
    private Sensor sensor;

    @Column(name = "TimeStamp", insertable = false, nullable = false)
    private Timestamp timeStamp;

    @Column(name = "ConditionsSelfResolved")
    private boolean conditionsSelfResolved;

    @Column(name = "UserActionTaken")
    private boolean userActionTaken;

    @ManyToOne
    @JoinColumn(name = "ActionId", foreignKey = @ForeignKey(name = "FK_Notification_Action"))
    private Action action;

    @Formula("CASE WHEN UserActionTaken = 0 AND ConditionsSelfResolved = 0 THEN 1 ELSE 0 END")
    private boolean isActive;
}
