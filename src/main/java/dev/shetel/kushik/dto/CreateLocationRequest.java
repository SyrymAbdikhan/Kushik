package dev.shetel.kushik.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateLocationRequest {
    @NotBlank
    private String city;

    @NotBlank
    @Size(min = 2, max = 2)
    private String countryCode;
}
