package dev.shetel.kushik.dto.response;

import dev.shetel.kushik.model.enumeration.ListingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class ListingDto {
    private Long listingId;
    private String title;
    private String description;
    private UserDto shelter;
    private LocationDto location;
    private Set<TagDto> tags;
    private ListingStatus status;
    private LocalDateTime createdAt;
}
