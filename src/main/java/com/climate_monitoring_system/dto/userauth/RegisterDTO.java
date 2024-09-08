package com.climate_monitoring_system.dto.userauth;

import lombok.Data;

@Data
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
