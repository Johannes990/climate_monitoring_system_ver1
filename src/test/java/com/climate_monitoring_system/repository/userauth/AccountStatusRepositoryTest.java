package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.AccountStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountStatusRepositoryTest {
    @Autowired
    private AccountStatusRepository accountStatusRepository;
    final String statusName = "status 1";
    final String statusDesc = "testing status numero uno";

    public AccountStatus accountStatusEntityGen() {
        AccountStatus accountStatus = new AccountStatus();

        accountStatus.setStatusName(statusName);
        accountStatus.setStatusDescription(statusDesc);

        return accountStatus;
    }

    public AccountStatus saveAccountStatus(AccountStatus accountStatus) {
        return accountStatusRepository.save(accountStatus);
    }

    @Test
    public void testAccountStatusRepositoryNotNull() {
        assertThat(accountStatusRepository).isNotNull();
    }

    @Test
    public void testAccountStatusRepositoryEmpty() {
        long count = accountStatusRepository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void testAccountStatusRepositorySave() {
        saveAccountStatus(accountStatusEntityGen());

        long count = accountStatusRepository.count();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void testAccountStatusRepositorySaveIsTransactional() {
        saveAccountStatus(accountStatusEntityGen());

        long count = accountStatusRepository.count();
        assertThat(count).isNotEqualTo(2);
        assertThat(count).isEqualTo(1);

    }

    @Test
    public void testAccountStatusRepositoryDelete() {
        AccountStatus accountStatus = saveAccountStatus(accountStatusEntityGen());
        accountStatusRepository.delete(accountStatus);

        long count = accountStatusRepository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void testAccountStatusRepositoryEntityValuesCorrect() {
        AccountStatus savedStatus = saveAccountStatus(accountStatusEntityGen());

        AccountStatus foundStatus = accountStatusRepository.getReferenceById(savedStatus.getStatusId());

        assertThat(foundStatus.getStatusName()).isEqualTo(statusName);
        assertThat(foundStatus.getStatusDescription()).isEqualTo(statusDesc);
    }
}
