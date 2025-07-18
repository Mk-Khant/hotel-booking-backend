package org.example.hotelmanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public record LoginRequest(String username, String password) {

    }

    public record RegisterRequest(String username, String password, String email, String phoneNumber) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String returnString = authService.register(registerRequest.username, registerRequest.password,registerRequest.email, registerRequest.phoneNumber);
        return ResponseEntity.ok().body(Map.of(
                "Return String",returnString,
                "status","successfully register"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String returnString = authService.login(loginRequest.username, loginRequest.password);
        return ResponseEntity.ok(returnString);
    }
}
