package dev.shetel.kushik.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateListingRequest {
    @Size(max = 250)
    private String title;
    private String description;
    private Long locationId;
    private Set<Long> tagIdsToAdd;
    private Set<Long> tagIdsToRemove;
    private String status;
}
