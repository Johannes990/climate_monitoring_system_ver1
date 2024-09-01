package com.climate_monitoring_system.dto.userauth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
