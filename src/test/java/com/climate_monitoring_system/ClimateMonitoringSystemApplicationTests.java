package com.climate_monitoring_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.climate_monitoring_system.repository.userauth.AccountStatusRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClimateMonitoringSystemApplicationTests {
	private AccountStatusRepository accountStatusRepository;

	@Test
	void contextLoads() {
		String value = null;

		//assertThat(accountStatusRepository).isNotNull();
		assertThat(value).isNotNull();
	}

}
