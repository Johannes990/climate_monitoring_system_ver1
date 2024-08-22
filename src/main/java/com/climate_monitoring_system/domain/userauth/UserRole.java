package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UserRole", schema = "userauth")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private int roleId;

    @Column(name = "RoleName")
    @NotNull
    private String roleName;

    @Column(name = "RoleDescription")
    private String roleDescription;
}
