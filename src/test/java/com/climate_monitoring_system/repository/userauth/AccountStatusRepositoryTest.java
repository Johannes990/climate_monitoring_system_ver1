package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountStatusRepositoryTest {
    @Autowired
    private AccountStatusRepository accountStatusRepository;
    @Autowired
    private TestEntityManager entityManager;
    final String accountStatusName = "operational";
    final String accountStatusDescription = "account is active, no issues";

    public AccountStatus accountStatusEntityGen(String statusName, String statusDescription) {
        AccountStatus accountStatus = new AccountStatus();

        accountStatus.setStatusName(statusName);
        accountStatus.setStatusDescription(statusDescription);

        return accountStatus;
    }

    public AccountStatus saveAccountStatus(AccountStatus accountStatus) {
        return accountStatusRepository.save(accountStatus);
    }

    @Test
    public void testAccountStatusRepositoryNotNull() {
        assertThat(accountStatusRepository)
                .isNotNull();
    }

    @Test
    public void testAccountStatusRepositorySaveSuccess() {
        AccountStatus createdAccountStatus = accountStatusEntityGen(
                accountStatusName,
                accountStatusDescription
        );
        AccountStatus savedAccountStatus = saveAccountStatus(createdAccountStatus);
        assertThat(entityManager.find(AccountStatus.class, savedAccountStatus.getStatusId()))
                .isEqualTo(createdAccountStatus);
    }

    @Test
    public void testAccountStatusRepositoryUpdateSuccess() {
        final String newStatusName = "limitation #1";
        final String newStatusDescription = "account usable only in view-mode";
        AccountStatus accountStatus = accountStatusEntityGen(
                accountStatusName,
                accountStatusDescription
        );
        entityManager.persist(accountStatus);
        accountStatus.setStatusName(newStatusName);
        accountStatus.setStatusDescription(newStatusDescription);
        saveAccountStatus(accountStatus);
        assertThat(entityManager.find(AccountStatus.class, accountStatus.getStatusId())
                .getStatusName()).isEqualTo(newStatusName);
        assertThat(entityManager.find(AccountStatus.class, accountStatus.getStatusId())
                .getStatusDescription()).isEqualTo(newStatusDescription);
    }

    @Test
    public void testAccountStatusRepositoryFindByIdSuccess() {
        AccountStatus accountStatus = accountStatusEntityGen(
                accountStatusName,
                accountStatusDescription
        );
        entityManager.persist(accountStatus);
        Optional<AccountStatus> foundAccountStatus = accountStatusRepository
                .findById(accountStatus.getStatusId());
        assertThat(foundAccountStatus).contains(accountStatus);
    }

    @Test
    public void testAccountStatusRepositoryDeleteSuccess() {
        AccountStatus accountStatus = accountStatusEntityGen(
                accountStatusName,
                accountStatusDescription
        );
        entityManager.persist(accountStatus);
        accountStatusRepository.delete(accountStatus);
        assertThat(entityManager.find(AccountStatus.class, accountStatus.getStatusId()))
                .isNull();
    }
}
