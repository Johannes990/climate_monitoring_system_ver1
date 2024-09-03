package com.climate_monitoring_system.controller.userauth;

import com.climate_monitoring_system.dto.userauth.LoginDTO;
import com.climate_monitoring_system.dto.userauth.RegisterDTO;
import com.climate_monitoring_system.service.userauth.AccountService;
import com.climate_monitoring_system.service.userauth.AuthenticationService;
import com.climate_monitoring_system.service.userauth.PasswordService;
import com.climate_monitoring_system.service.userauth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final PasswordService passwordService;
    private final UserService userService;

    @PostMapping("login")
    public boolean loginUser(@RequestBody LoginDTO loginDTO) {
        return authenticationService.loginUser(loginDTO);
    }

    @PostMapping("register")
    public boolean registerUser(@RequestBody RegisterDTO registerDTO) {
        return authenticationService.registerUser(registerDTO);
    }

    @GetMapping("/")
    public String getRoot() {
        return "This is working";
    }

    @GetMapping("/account/{id}")
    public String getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id).toString();
    }

    @GetMapping("/accountstring/{accountType}")
    public String getAccount(@PathVariable String accountType) {
        return accountService.getAccountByAccountType(accountType).toString();
    }

    @GetMapping("/hashpass/{pass}")
    public String getPassHash(@PathVariable String pass) {
        return passwordService.encodePassword(pass);
    }

    @GetMapping("/find/{userName}")
    public String getUserByUserName(@PathVariable String userName) {
        return userService.findByUserName(userName).toString();
    }
}
