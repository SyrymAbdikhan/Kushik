package dev.shetel.kushik.dto.request;

import dev.shetel.kushik.model.enumeration.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateApplicationRequest {
    @NotNull(message = "Status was not provided")
    private ApplicationStatus status;
}
