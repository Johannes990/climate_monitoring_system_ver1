package com.climate_monitoring_system.dto.notification;

import com.climate_monitoring_system.dto.userauth.UserDTO;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ActionDTO {
    private long actionId;
    private Timestamp timestamp;
    private String message;
    private UserDTO user;
}
