package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAccountRepositoryNotNull() {
        assertThat(accountRepository).isNotNull()  ;
    }

    @Test
    public void testAccountRepositoryHoldsDefaultAccounts() {
        List<Account> accounts = accountRepository.findAll();
        final List<String> accountTypes = Arrays.asList("Administrator", "Regular", "View only");

        // we should have 3 accounts in db from liquibase changelogs
        // 'Administrator', 'Regular' and 'View only'
        assertThat(accounts.size()).isEqualTo(3);

        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            assertThat(account.getAccountType()).isEqualTo(accountTypes.get(i));
        }
    }

    @Test
    public void testAccountRepositoryCrudOperations() {
        final String accountTypeInitial = "Testing account";
        final String accountTypeChanged = "Changed account";
        Account newAccount = new Account();
        newAccount.setAccountType(accountTypeInitial);

        Account savedAccount = entityManager.persistAndFlush(newAccount);
        assertThat(savedAccount.getAccountId()).isNotNull();

        savedAccount.setAccountType(accountTypeChanged);

        accountRepository.save(savedAccount);
        assertThat(entityManager.find(Account.class, savedAccount.getAccountId()).getAccountType()).isEqualTo(accountTypeChanged);

        accountRepository.delete(savedAccount);
        assertThat(entityManager.find(Account.class, savedAccount.getAccountId())).isNull();
    }
}
