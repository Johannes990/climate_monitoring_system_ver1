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
    final String email = "Johannes.Jyrgenson@Note-EMS.com";
    final String firstName = "Johannes";
    final String lastName = "Jyrgenson";
    final String userName = "System Creator";

    @Test
    public void testUserRepositoryNotNull() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void testUserRepositoryHoldsDefaultAccounts() {
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
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        assertThat(optionalUser.isPresent()).isTrue();

        AppUser user = optionalUser.get();
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getUserName()).isEqualTo(user);
    }

    @Test
    public void testUserRepositoryFindByUserName() {
        Optional<AppUser> optionalUser = userRepository.findByUserName(userName);
        assertThat(optionalUser.isPresent()).isTrue();

        AppUser user = optionalUser.get();
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void testUserRepositoryCrudOperations() {
        final String createdFirstName = "Andre";
        final String createdLastName = "Cabanoss";
        final String createdUserName = "Spanish guy";
        final String updatedUserName = "Hacker guy";
        final String createdEmail = "Spanish@Spanish.com";
        final String updatedEmail = "hacker@Spanish.com";
        final String createdPasswordHash = "Spanishwef984ewv654grv64";
        final long viewOnlyAccountId = 3L;

        Account viewOnlyAccount = accountRepository.getReferenceById(viewOnlyAccountId);
        AppUser newUser = new AppUser();
        newUser.setFirstName(createdFirstName);
        newUser.setLastName(createdLastName);
        newUser.setUserName(createdUserName);
        newUser.setEmail(createdEmail);
        newUser.setPasswordHash(createdPasswordHash);
        newUser.setAccountId(viewOnlyAccount);

        AppUser savedUser = entityManager.persistAndFlush(newUser);
        assertThat(savedUser.getUserId()).isNotNull();

        savedUser.setEmail(updatedEmail);
        savedUser.setUserName(updatedUserName);

        userRepository.save(savedUser);
        assertThat(entityManager.find(AppUser.class, savedUser.getUserId()).getUserName())
                .isEqualTo(updatedUserName);
        assertThat(entityManager.find(AppUser.class, savedUser.getUserId()).getEmail())
                .isEqualTo(updatedEmail);

        userRepository.delete(savedUser);
        assertThat(entityManager.find(AppUser.class, savedUser.getUserId())).isNull();
    }
}
