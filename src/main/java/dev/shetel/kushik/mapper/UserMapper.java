package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.CreateUserRequest;
import dev.shetel.kushik.dto.UpdateUserRequest;
import dev.shetel.kushik.dto.UserDto;
import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : UserRole.ADOPTER)
                .build();
    }
}
