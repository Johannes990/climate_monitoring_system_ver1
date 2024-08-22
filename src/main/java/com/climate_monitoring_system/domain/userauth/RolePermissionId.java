package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolePermissionId implements Serializable {
    private int roleId;
    private int permissionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePermissionId objRPId = (RolePermissionId) o;
        return objRPId.roleId == this.roleId && objRPId.permissionId == this.permissionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.roleId, this.permissionId);
    }
}
