package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.domain.userauth.AppUser;
import com.climate_monitoring_system.dto.userauth.LoginDTO;
import com.climate_monitoring_system.dto.userauth.RegisterDTO;
import com.climate_monitoring_system.repository.userauth.AccountRepository;
import com.climate_monitoring_system.repository.userauth.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordService passwordService;

    public boolean loginUser(LoginDTO loginDTO) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
        if (optionalUser.isEmpty()) {
            return false;
        }

        AppUser user = optionalUser.get();

        return passwordService.validatePassword(loginDTO.getPassword(), user.getPasswordHash());
    };

    public boolean registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            return false; // email already used
        }

        String hashedPassword = passwordService.encodePassword(registerDTO.getPassword());
        // when creating user, 'View only' is the default access level
        Optional<Account> optionalAccount = accountRepository.findByAccountType("View only");

        if (optionalAccount.isEmpty()) {
            return false;
        }

        Account account = optionalAccount.get();

        AppUser newUser = new AppUser();
        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());
        newUser.setUserName(registerDTO.getUsername());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPasswordHash(hashedPassword);
        newUser.setAccountId(account);
        userRepository.save(newUser);

        return true;
    }
}
