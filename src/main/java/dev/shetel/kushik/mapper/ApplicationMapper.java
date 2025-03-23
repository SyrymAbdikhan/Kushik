package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.request.CreateApplicationRequest;
import dev.shetel.kushik.dto.response.ApplicationDto;
import dev.shetel.kushik.model.Application;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.enumeration.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
    private final UserMapper userMapper;
    private final ListingMapper listingMapper;

    public ApplicationDto toDto(Application application) {
        return ApplicationDto.builder()
                .applicationId(application.getApplicationId())
                .adopter(userMapper.toDto(application.getAdopter()))
                .listing(listingMapper.toDto(application.getListing()))
                .message(application.getMessage())
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }

    public Application toEntity(CreateApplicationRequest request, User adopter, Listing listing) {
        return Application.builder()
                .adopter(adopter)
                .listing(listing)
                .message(request.getMessage())
                .status(ApplicationStatus.PENDING)
                .build();
    }
}
