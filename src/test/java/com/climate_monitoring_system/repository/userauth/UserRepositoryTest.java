package com.climate_monitoring_system.repository.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.domain.userauth.AppUser;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testUserRepositoryNotNull() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void testUserRepositoryHoldsDefaultAccounts() {
        final long initialAccountCount = 4;
        final List<String> actualNames = Arrays.asList(
                "Johannes Jyrgenson",
                "Mees Metsast",
                "Naine Polvast",
                "ahv 2"
        );
        final List<String> userNames = Arrays.asList(
                "System Creator",
                "Metsavaht",
                "IWillWin",
                "ahv2"
        );
        final List<String> emails = Arrays.asList(
                "Johannes.Jyrgenson@Note-EMS.com",
                "metsavaht@note-ems.com",
                "winners@lost.com",
                "vale@email.com"
        );

        List<AppUser> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(initialAccountCount);

        for (int i = 0; i < initialAccountCount; i++) {
            AppUser user = users.get(i);
            assertThat(user.getFirstName() + " " + user.getLastName()).isEqualTo(actualNames.get(i));
            assertThat(user.getUserName()).isEqualTo(userNames.get(i));
            assertThat(user.getEmail()).isEqualTo(emails.get(i));
        }
    }

    @Test
    public void testUserRepositoryFindByEmail() {
        final String email = "vale@email.com";
        final String expectedFirstName = "ahv";
        final String expectedLastName = "2";
        final String expectedUserName = "ahv2";

        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        assertThat(optionalUser.isPresent()).isTrue();

        AppUser user = optionalUser.get();
        assertThat(user.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(user.getLastName()).isEqualTo(expectedLastName);
        assertThat(user.getUserName()).isEqualTo(expectedUserName);
    }

    @Test
    public void testUserRepositoryFindByUserName() {
        final String userName = "System Creator";
        final String expectedFirstName = "Johannes";
        final String expectedLastName = "Jyrgenson";
        final String expectedEmail = "Johannes.Jyrgenson@Note-EMS.com";

        Optional<AppUser> optionalUser = userRepository.findByUserName(userName);
        assertThat(optionalUser.isPresent()).isTrue();

        AppUser user = optionalUser.get();
        assertThat(user.getFirstName()).isEqualTo(expectedFirstName);
        assertThat(user.getLastName()).isEqualTo(expectedLastName);
        assertThat(user.getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    public void testUserRepositoryCrudOperations() {
        final String firstName = "Andre";
        final String lastName = "Cabanoss";
        final String userName = "Spanish guy";
        final String newUserName = "Hacker guy";
        final String email = "Spanish@Spanish.com";
        final String newEmail = "hacker@Spanish.com";
        final String passwordHash = "Spanishwef984ewv654grv64";
        final long viewOnlyAccountId = 3L;

        Account viewOnlyAccount = accountRepository.getReferenceById(viewOnlyAccountId);
        AppUser newUser = new AppUser();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordHash);
        newUser.setAccountId(viewOnlyAccount);

        AppUser savedUser = entityManager.persistAndFlush(newUser);
        assertThat(savedUser.getUserId()).isNotNull();

        savedUser.setEmail(newEmail);
        savedUser.setUserName(newUserName);

        userRepository.save(savedUser);
        assertThat(entityManager.find(AppUser.class, savedUser.getUserId()).getUserName())
                .isEqualTo(newUserName);
        assertThat(entityManager.find(AppUser.class, savedUser.getUserId()).getEmail())
                .isEqualTo(newEmail);

        userRepository.delete(savedUser);
        assertThat(entityManager.find(AppUser.class, savedUser.getUserId())).isNull();
    }
}
