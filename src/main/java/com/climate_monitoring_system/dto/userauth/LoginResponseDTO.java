package com.climate_monitoring_system.dto.userauth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
}
