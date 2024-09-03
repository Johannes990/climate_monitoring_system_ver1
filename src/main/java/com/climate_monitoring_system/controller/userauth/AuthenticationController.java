package com.climate_monitoring_system.controller.userauth;

import com.climate_monitoring_system.dto.userauth.LoginDTO;
import com.climate_monitoring_system.dto.userauth.RegisterDTO;
import com.climate_monitoring_system.service.userauth.AccountService;
import com.climate_monitoring_system.service.userauth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AccountService accountService;

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
}
