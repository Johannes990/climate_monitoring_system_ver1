package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.domain.userauth.AppUser;
import com.climate_monitoring_system.dto.userauth.AccountDTO;
import com.climate_monitoring_system.dto.userauth.UserDTO;
import com.climate_monitoring_system.repository.userauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final UserRepository userRepository;

    public UserDTO findByUserName(String userName) {
        Optional<AppUser> optionalUser = userRepository.findByUserName(userName);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            return makeUserDTO(user);
        }

        return new UserDTO();
    }

    public UserDTO findByUserId(long id) {
        Optional<AppUser> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            AppUser user  = optionalUser.get();
            return makeUserDTO(user);
        }

        return new UserDTO();
    }

    public List<UserDTO> findAll() {
        List<AppUser> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (AppUser user : users) {
            UserDTO userDTO = makeUserDTO(user);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    public UserDTO findByEmail(String email) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            System.out.println("found user " + user + " with email " + email);
            return makeUserDTO(user);
        }

        return new UserDTO();
    }

    private AccountDTO getAccountDTO(Account account) {
        AccountDTO newDTO = new AccountDTO();
        newDTO.setAccountId(account.getAccountId());
        newDTO.setAccountType(account.getAccountType());
        return newDTO;
    }

    private UserDTO makeUserDTO(AppUser user) {
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
}
