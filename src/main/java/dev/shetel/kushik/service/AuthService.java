package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.*;
import dev.shetel.kushik.model.UserRole;
import dev.shetel.kushik.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token).build();
    }

    public UserDto register(RegistrationRequest request) {
        UserRole role = request.getRole() != null ? request.getRole() : UserRole.ADOPTER;
        if (role == UserRole.ADMIN) {
            throw new IllegalArgumentException("Admin registration not allowed");
        }

        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.setUsername(request.getUsername());
        createRequest.setEmail(request.getEmail());
        createRequest.setPassword(request.getPassword());
        createRequest.setRole(role);

        return userService.createUser(createRequest);
    }
}