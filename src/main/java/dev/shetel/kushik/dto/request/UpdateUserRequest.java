package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Email(message = "Invalid email address")
    private String email;

    @Size(min = 3, max = 20, message = "Username must be between {min} and {max} characters long")
    private String username;

    @Size(min = 8, max=50, message = "Password must be between {min} and {max} characters long")
    private String password;
}
