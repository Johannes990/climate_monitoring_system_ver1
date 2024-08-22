package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "RolePermissionMap", schema = "userauth")
public class RolePermissionMap {
    @EmbeddedId
    private RolePermissionId id;

    @MapsId("roleId")
    @JoinColumn(name = "RoleID")
    @ManyToOne
    private UserRole role;

    @MapsId("permissionId")
    @JoinColumn(name = "PermissionID")
    @ManyToOne
    private Permission permission;
}
