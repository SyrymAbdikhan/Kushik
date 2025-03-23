package dev.shetel.kushik.dto.request;

import dev.shetel.kushik.model.enumeration.ListingStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateListingRequest {
    @Size(max = 250, message = "Title can not exceed {max} characters")
    private String title;
    private String description;
    private Long locationId;
    private Set<Long> tagIdsToAdd;
    private Set<Long> tagIdsToRemove;
    private ListingStatus status;
}
