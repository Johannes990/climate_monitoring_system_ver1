package com.climate_monitoring_system.controller.userauth;

import com.climate_monitoring_system.dto.userauth.LoginDTO;
import com.climate_monitoring_system.dto.userauth.RegisterDTO;
import com.climate_monitoring_system.dto.userauth.UserDTO;
import com.climate_monitoring_system.service.userauth.AuthenticationService;
import com.climate_monitoring_system.service.userauth.AppUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.climate_monitoring_system.controller.Paths.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AppUserService appUserService;

    @PostMapping(LOGIN_QUERY_PATH)
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        if (authenticationService.loginUser(loginDTO)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", loginDTO.getEmail());
            return ResponseEntity.ok("Login Successful!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
    }

    @GetMapping(LOGOUT_QUERY_PATH)
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10);
        response.addCookie(cookie);
        return ResponseEntity.ok("Logout Successful!");
    }

    @PostMapping(REGISTER_QUERY_PATH)
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {
        boolean registrationSuccess = authenticationService.registerUser(registerDTO);

        if (registrationSuccess) {
            return ResponseEntity.ok("Registration Successful!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Failed!!");
    }

    @GetMapping(USERS_QUERY_PATH)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> foundUsers = appUserService.findAll();
        return ResponseEntity.ok(foundUsers);
    }

    @GetMapping(PROTECTED_QUERY_PATH)
    public ResponseEntity<String> protectedResource(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return ResponseEntity.ok("Access to protected resources granted!");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access to protected resources denied!");
    }
}
