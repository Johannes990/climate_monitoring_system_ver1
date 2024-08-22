package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Permission", schema = "userauth")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PermissionID")
    private int permissionId;

    @Column(name = "PermissionName")
    @NotNull
    private String permissionName;

    @Column(name = "PermissionDescription")
    private String permissionDescription;
}
