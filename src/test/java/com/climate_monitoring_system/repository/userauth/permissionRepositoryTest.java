package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class permissionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PermissionRepository permissionRepository;
    final String permissionName = "permissionName";
    final String permissionDescription = "permissionDescription";

    public Permission permissionEntityGen(String statusName, String permissionStatus) {
        Permission permission = new Permission();
    }

    public Permission saveAccountStatus(Permission permission) {
        return permissionRepository.save(permission);
    }
}
