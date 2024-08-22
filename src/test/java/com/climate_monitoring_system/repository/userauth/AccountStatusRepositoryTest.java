package com.climate_monitoring_system.repository.userauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.climate_monitoring_system.repository.userauth.AccountStatusRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AccountStatusRepositoryTest {
    
    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Test
    public void testAccountStatusRepositoryNotNull() {
        assertThat(accountStatusRepository).isNotNull();
    }
}
