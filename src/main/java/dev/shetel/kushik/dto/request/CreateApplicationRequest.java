package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateApplicationRequest {
    @NotNull(message = "ListingId was not provided")
    private Long listingId;

    @NotBlank(message = "Message was not provided")
    private String message;
}
