package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.response.JwtResponse;
import dev.shetel.kushik.dto.request.LoginRequest;
import dev.shetel.kushik.dto.request.RegistrationRequest;
import dev.shetel.kushik.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
