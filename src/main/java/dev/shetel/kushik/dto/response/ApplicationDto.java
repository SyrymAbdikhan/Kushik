package dev.shetel.kushik.dto.response;

import dev.shetel.kushik.model.enumeration.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationDto {
    private Long applicationId;
    private UserDto adopter;
    private ListingDto listing;
    private String message;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
