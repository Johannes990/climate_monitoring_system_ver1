package com.climate_monitoring_system.dto.userauth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserAccountInfoDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int genderId;
    private int roleId;
}
