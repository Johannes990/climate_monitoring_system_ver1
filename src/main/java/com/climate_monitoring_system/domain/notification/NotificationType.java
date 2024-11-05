package com.climate_monitoring_system.domain.notification;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "NotificationType", schema = "notification")
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationTypeId")
    private long notificationTypeId;

    @Column(name = "NotificationTypeDescription")
    @NotNull
    private String notificationTypeDescription;
}
