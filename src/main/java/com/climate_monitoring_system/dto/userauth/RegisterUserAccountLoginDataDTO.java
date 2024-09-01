package com.climate_monitoring_system.dto.userauth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterUserAccountLoginDataDTO {
    @Email
    private String email;
    private String password;
    private int hashAlgorithmId;
}
