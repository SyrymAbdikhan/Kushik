package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.CreateUserRequest;
import dev.shetel.kushik.dto.LoginRequest;
import dev.shetel.kushik.dto.RegistrationRequest;
import dev.shetel.kushik.model.enumeration.UserRole;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public CreateUserRequest toCreateUserRequest(RegistrationRequest request) {
        return CreateUserRequest.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole() != null ? request.getRole() : UserRole.ADOPTER)
                .build();
    }

    public LoginRequest toLoginRequest(RegistrationRequest request) {
        return LoginRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
