package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class CreateListingRequest {
    @NotBlank(message = "Title was not provided")
    @Size(max = 250, message = "Title can not exceed {max} characters")
    private String title;

    private String description;
    private Long locationId;
    private Set<Long> tagIds;
}
