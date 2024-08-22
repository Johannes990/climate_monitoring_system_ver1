package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "RolePermissionMap", schema = "userauth")
public class RolePermissionMap {
    @Id
    @JoinColumn(name = "RoleID")
    @ManyToOne
    private int roleId;

    @Id
    @JoinColumn(name = "PermissionID")
    @ManyToOne
    private int permissionId;
}
