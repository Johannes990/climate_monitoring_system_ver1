package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.domain.userauth.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAccountChangesPropagateToAppUser() {
        final String accountType = "totally new account type";
        final String changedAccountType = "changed account type";
        final String firstName = "Jane";
        final String lastName = "Doe";
        final String userName = "jane.doe";
        final String email = "jane.doe@example.com";
        final String passwordHash = "hashedPassword789";

        Account newAccount = new Account();
        newAccount.setAccountType(accountType);
        Account savedAccount = entityManager.persistAndFlush(newAccount);
        assertThat(savedAccount.getAccountId()).isNotNull();

        AppUser newUser = new AppUser();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordHash);
        newUser.setAccountId(newAccount);

        AppUser savedUser = entityManager.persistAndFlush(newUser);
        assertThat(savedUser.getUserId()).isNotNull();
        savedAccount.setAccountType(changedAccountType);
        accountRepository.saveAndFlush(savedAccount);

        assertThat(entityManager.find(AppUser.class, savedUser.getUserId()).getAccountId()
                .getAccountType()).isEqualTo(changedAccountType);
    }
}
