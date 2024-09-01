package com.climate_monitoring_system.mapper.userauth;

import com.climate_monitoring_system.domain.userauth.UserAccountInfo;
import com.climate_monitoring_system.domain.userauth.UserAccountLoginData;
import com.climate_monitoring_system.dto.userauth.LoginResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "UserAccountInfo.firstName", target = "firstName")
    @Mapping(source = "UserAccountInfo.lastName", target = "lastName")
    @Mapping(source = "UserAccountInfo.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "UserAccountInfo.gender.genderName", target = "gender")
    LoginResponseDTO toLoginResponseDTO(UserAccountLoginData userAccountLoginData);
}
