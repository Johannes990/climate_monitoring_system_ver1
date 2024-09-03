package com.climate_monitoring_system.repository.userauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class permissionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PermissionRepository permissionRepository;
    final String permissionName = "permissionName";
    final String permissionDescription = "permissionDescription";

    public Permission permissionEntityGen(String permissionName, String permissionStatus) {
        Permission permission = new Permission();
        permission.setPermissionName(permissionName);
        permission.setPermissionDescription(permissionDescription);
        return permission;
    }

    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public void testPermissionRepositoryNotNull() {
        assertThat(permissionRepository).isNotNull();
    }
}
