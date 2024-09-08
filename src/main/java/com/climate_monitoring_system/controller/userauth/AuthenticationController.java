package com.climate_monitoring_system.controller.userauth;

import com.climate_monitoring_system.dto.userauth.AccountDTO;
import com.climate_monitoring_system.dto.userauth.LoginDTO;
import com.climate_monitoring_system.dto.userauth.RegisterDTO;
import com.climate_monitoring_system.dto.userauth.UserDTO;
import com.climate_monitoring_system.service.userauth.AccountService;
import com.climate_monitoring_system.service.userauth.AuthenticationService;
import com.climate_monitoring_system.service.userauth.PasswordService;
import com.climate_monitoring_system.service.userauth.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final PasswordService passwordService;
    private final AppUserService userService;
    private final AppUserService appUserService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        if (authenticationService.loginUser(loginDTO)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", loginDTO.getEmail());
            return ResponseEntity.ok("Login Successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout Successful!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {
        boolean registrationSuccess = authenticationService.registerUser(registerDTO);
        if (registrationSuccess) {
            return ResponseEntity.ok("Registration Successful!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Failed!!");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> foundUsers = appUserService.findAll();
        return ResponseEntity.ok(foundUsers);
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedResource(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return ResponseEntity.ok("Access to protected resources granted!");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access to protected resources denied!");
        }
    }
}
