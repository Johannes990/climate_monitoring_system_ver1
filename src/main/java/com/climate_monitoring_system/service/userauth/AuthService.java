package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.UserAccountLoginData;
import com.climate_monitoring_system.dto.userauth.LoginRequestDTO;
import com.climate_monitoring_system.dto.userauth.LoginResponseDTO;
import com.climate_monitoring_system.mapper.userauth.UserMapper;
import com.climate_monitoring_system.repository.userauth.UserAccountLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserAccountLoginDataRepository userAccountLoginDataRepository;
    @Autowired
    private UserMapper userMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<UserAccountLoginData> loginDataOptional = userAccountLoginDataRepository
                .findByEmailIgnoreCase(loginRequestDTO.getEmail());
        if (loginDataOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with email: " + loginRequestDTO.getEmail());
        }

        UserAccountLoginData loginData = loginDataOptional.get();
        boolean isPasswordValid = passwordService.verifyPassword(
                loginRequestDTO.getPassword(),
                loginData.getPasswordHash(), 
                loginData.getPasswordSalt(),
                loginData.getHashAlgorithm()
        );

        if (!isPasswordValid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return userMapper.toLoginResponseDTO(loginData);
    }
}
