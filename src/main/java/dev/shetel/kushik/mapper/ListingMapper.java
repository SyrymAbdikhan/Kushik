package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.request.CreateListingRequest;
import dev.shetel.kushik.dto.response.ListingDto;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.enumeration.ListingStatus;
import dev.shetel.kushik.service.LocationService;
import dev.shetel.kushik.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListingMapper {
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;
    private final TagMapper tagMapper;
    private final LocationService locationService;
    private final TagService tagService;

    public ListingDto toDto(Listing listing) {
        return ListingDto.builder()
                .listingId(listing.getListingId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .shelter(userMapper.toDto(listing.getShelter()))
                .location(locationMapper.toDto(listing.getLocation()))
                .tags(listing.getTags().stream()
                        .map(tagMapper::toDto)
                        .collect(Collectors.toSet()))
                .status(listing.getStatus())
                .updatedAt(listing.getUpdatedAt())
                .createdAt(listing.getCreatedAt())
                .build();
    }

    public Listing toEntity(CreateListingRequest request, User shelter) {
        Listing listing = Listing.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .shelter(shelter)
                .location(locationService.getLocationById(request.getLocationId()))
                .status(ListingStatus.AVAILABLE)
                .build();

        if (request.getTagIds() != null) {
            Set<Tag> tags = tagService.getTagByIds(request.getTagIds());
            listing.setTags(tags);
        }

        return listing;
    }
}
