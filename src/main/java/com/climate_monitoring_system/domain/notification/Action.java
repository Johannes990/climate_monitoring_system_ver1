package com.climate_monitoring_system.domain.notification;

import com.climate_monitoring_system.domain.userauth.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Action", schema = "notification")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActionId")
    private long actionId;

    @Column(name = "TimeStamp", insertable = false, updatable = false)
    private Timestamp timestamp;

    @Column(name = "Message")
    @NotNull
    private String message;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private AppUser user;
}
