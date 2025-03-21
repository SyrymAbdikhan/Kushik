package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.*;
import dev.shetel.kushik.model.enumeration.UserRole;
import dev.shetel.kushik.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public JwtResponse login(LoginRequest request) {
        final UserDetails userDetails = jwtTokenUtil.authenticate(
                request.getEmail(),
                request.getPassword()
        );
        final String token = jwtTokenUtil.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token).build();
    }

    public JwtResponse register(RegistrationRequest request) {
        UserRole role = request.getRole() != null ? request.getRole() : UserRole.ADOPTER;
        if (role == UserRole.ADMIN) {
            throw new IllegalArgumentException("Admin registration not allowed");
        }

        CreateUserRequest createRequest = modelMapper.map(request, CreateUserRequest.class);
        UserDto user = userService.createUser(createRequest);
        return login(
                modelMapper.map(request, LoginRequest.class)
        );
    }
}