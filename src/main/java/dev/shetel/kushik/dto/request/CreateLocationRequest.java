package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateLocationRequest {
    @NotBlank(message = "City was not provided")
    private String city;

    @NotBlank(message = "CountryCode was not provided")
    @Size(min = 2, max = 2, message = "CountryCode must be 2 characters long")
    private String countryCode;
}
