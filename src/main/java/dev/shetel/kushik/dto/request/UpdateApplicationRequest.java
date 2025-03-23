package dev.shetel.kushik.dto.request;

import dev.shetel.kushik.model.enumeration.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateApplicationRequest {
    @NotBlank(message = "Status was not provided")
    private ApplicationStatus status;
}
