package com.climate_monitoring_system.dto.userauth;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
