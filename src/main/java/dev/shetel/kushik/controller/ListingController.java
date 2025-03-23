package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.request.CreateListingRequest;
import dev.shetel.kushik.dto.request.UpdateListingRequest;
import dev.shetel.kushik.dto.response.ListingDto;
import dev.shetel.kushik.mapper.ListingMapper;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;
    private final ListingMapper listingMapper;

    @PostMapping
    @PreAuthorize("hasRole('SHELTER')")
    public ResponseEntity<ListingDto> createListing(
            @Valid @RequestBody CreateListingRequest request
    ) {
        Listing listing = listingService.createListing(request);
        ListingDto listingDto = listingMapper.toDto(listing);
        return ResponseEntity.status(HttpStatus.CREATED).body(listingDto);
    }

    @PutMapping("/{listingId}")
    @PreAuthorize("hasRole('SHELTER')")
    public ResponseEntity<ListingDto> updateListing(
            @PathVariable Long listingId,
            @Valid @RequestBody UpdateListingRequest request
    ) {
        Listing updatedListing = listingService.updateListing(listingId, request);
        ListingDto listingDto = listingMapper.toDto(updatedListing);
        return ResponseEntity.ok(listingDto);
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ListingDto> getListing(@PathVariable Long listingId) {
        Listing listing = listingService.getListingById(listingId);
        ListingDto listingDto = listingMapper.toDto(listing);
        return ResponseEntity.ok(listingDto);
    }

    @GetMapping
    public ResponseEntity<List<ListingDto>> getListingsByTags(
            @RequestParam(required = false) Set<Long> tagIds
    ) {
        List<Listing> listings = listingService.getListingsByTags(tagIds);
        List<ListingDto> listingDtos = listings.stream()
                .map(listingMapper::toDto).toList();
        return ResponseEntity.ok(listingDtos);
    }

    @DeleteMapping("/{listingId}")
    @PreAuthorize("hasRole('SHELTER')")
    public ResponseEntity<Void> deleteListing(@PathVariable Long listingId) {
        listingService.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }
}
