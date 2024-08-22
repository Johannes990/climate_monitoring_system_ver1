package com.climate_monitoring_system.domain.userauth;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "AccountStatus", schema = "userauth")
public class AccountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private int statusId;

    @Column(name = "StatusName")
    @NotNull
    private String statusName;

    @Column(name = "StatusDescription")
    private String statusDescription;
}
