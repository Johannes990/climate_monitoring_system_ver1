package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.domain.userauth.AppUser;
import com.climate_monitoring_system.dto.userauth.AccountDTO;
import com.climate_monitoring_system.dto.userauth.UserDTO;
import com.climate_monitoring_system.repository.userauth.AccountRepository;
import com.climate_monitoring_system.repository.userauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO findByUserName(String userName) {
        Optional<AppUser> optionalUser = userRepository.findByUserName(userName);

        if (optionalUser.isEmpty()) {
            return new UserDTO();
        }

        AppUser user = optionalUser.get();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPasswordHash());
        userDTO.setAccount(getAccountDTO(user.getAccountId()));

        return userDTO;
    }

    private AccountDTO getAccountDTO(Account account) {
        AccountDTO newDTO = new AccountDTO();
        newDTO.setAccountId(account.getAccountId());
        newDTO.setAccountType(account.getAccountType());

        return newDTO;
    }
}
