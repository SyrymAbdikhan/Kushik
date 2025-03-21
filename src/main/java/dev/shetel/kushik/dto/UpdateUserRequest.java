package dev.shetel.kushik.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Email
    private String email;

    private String username;

    @Size(min = 8)
    private String password;
}
