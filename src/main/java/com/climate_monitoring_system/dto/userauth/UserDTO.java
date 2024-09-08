package com.climate_monitoring_system.dto.userauth;

import lombok.Data;

@Data
public class UserDTO {
    private long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private AccountDTO account;
}
