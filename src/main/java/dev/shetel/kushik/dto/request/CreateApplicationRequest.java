package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateApplicationRequest {
    @NotNull
    private Long listingId;

    @NotBlank
    private String message;
}
