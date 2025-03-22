package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.request.CreateListingRequest;
import dev.shetel.kushik.dto.request.UpdateListingRequest;
import dev.shetel.kushik.dto.response.ListingDto;
import dev.shetel.kushik.mapper.ListingMapper;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.Location;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.enumeration.ListingStatus;
import dev.shetel.kushik.model.enumeration.UserRole;
import dev.shetel.kushik.repository.ListingRepository;
import dev.shetel.kushik.repository.LocationRepository;
import dev.shetel.kushik.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserService userService;
    private final TagRepository tagRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public Listing createListing(CreateListingRequest request) {
        User shelter = userService.getCurrentUserEntity();
        if (shelter.getRole() != UserRole.SHELTER) {
            throw new IllegalArgumentException("Only shelters can create listings");
        }

        Listing listing = listingMapper.toEntity(request, shelter);
        return listingRepository.save(listing);
    }

    @Transactional
    public Listing updateListing(Long listingId, UpdateListingRequest request) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (!listing.getShelter().getUserId().equals(userService.getCurrentUser().getUserId())) {
            throw new AccessDeniedException("You don't own this listing");
        }

        if (listing.getStatus() == ListingStatus.ADOPTED) {
            throw new IllegalStateException("Adopted listings cannot be modified");
        }

        if (request.getTitle() != null) {
            listing.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            listing.setDescription(request.getDescription());
        }

        if (request.getLocationId() != null) {
            listing.setLocation(locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Location not found")));
        }

        if (request.getTagIdsToAdd() != null) {
            Set<Tag> newTags = new HashSet<>(tagRepository.findAllById(request.getTagIdsToAdd()));
            listing.getTags().addAll(newTags);
        }

        if (request.getTagIdsToRemove() != null) {
            listing.getTags().removeIf(tag ->
                    request.getTagIdsToRemove().contains(tag.getTagId()));
        }

        return listingRepository.save(listing);
    }

    public List<Listing> getListings() {
        return listingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Listing getListingById(Long listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));
    }

    @Transactional(readOnly = true)
    public List<Listing> getListingsByTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return getListings();
        }

        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tagIds));
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("One or more tags not found");
        }

        return listingRepository.findByTags(tags);
    }

    @Transactional(readOnly = true)
    public List<Listing> getListingsByLocation(Long locationId) {
        if (locationId == null) {
            return getListings();
        }

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        return listingRepository.findByLocation(location);
    }

    @Transactional
    public void deleteListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (!listing.getShelter().getUserId().equals(userService.getCurrentUser().getUserId())) {
            throw new AccessDeniedException("You don't own this listing");
        }

        listingRepository.delete(listing);
    }
}
