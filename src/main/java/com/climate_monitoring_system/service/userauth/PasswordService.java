package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.HashAlgorithm;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    public String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String rawPassword, String storedHash, HashAlgorithm algorithm) {
        if (algorithm.getHashAlgorithmName().equalsIgnoreCase("bcrypt")) {
            return BCrypt.checkpw(rawPassword, storedHash);
        }

        throw new IllegalArgumentException("Unsupported Hash Algorithm");
    }
}
