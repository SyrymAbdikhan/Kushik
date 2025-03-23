package dev.shetel.kushik.dto.request;

import dev.shetel.kushik.model.enumeration.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Email was not provided")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank
    @Size(min = 3, max = 20, message = "Username must be between {min} and {max} characters long")
    private String username;

    @NotBlank(message = "Password was not provided")
    @Size(min = 8, max=50, message = "Password must be between {min} and {max} characters long")
    private String password;

    private UserRole role;
}
