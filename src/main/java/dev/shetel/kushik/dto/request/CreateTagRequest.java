package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTagRequest {
    @NotBlank
    private String name;

    private boolean primary;
}