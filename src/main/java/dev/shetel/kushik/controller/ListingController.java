package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.request.CreateListingRequest;
import dev.shetel.kushik.dto.request.UpdateListingRequest;
import dev.shetel.kushik.dto.response.ListingDto;
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

    @PostMapping
    @PreAuthorize("hasRole('SHELTER')")
    public ResponseEntity<ListingDto> createListing(
            @Valid @RequestBody CreateListingRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listingService.createListing(request));
    }

    @PutMapping("/{listingId}")
    @PreAuthorize("hasRole('SHELTER')")
    public ResponseEntity<ListingDto> updateListing(
            @PathVariable Long listingId,
            @Valid @RequestBody UpdateListingRequest request
    ) {
        return ResponseEntity.ok(listingService.updateListing(listingId, request));
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ListingDto> getListing(@PathVariable Long listingId) {
        return ResponseEntity.ok(listingService.getListingById(listingId));
    }

    @GetMapping
    public ResponseEntity<List<ListingDto>> searchListings(
            @RequestBody(required = false) Set<Long> tagIds
    ) {
        return ResponseEntity.ok(listingService.getListingsByTags(tagIds));
    }

    @DeleteMapping("/{listingId}")
    @PreAuthorize("hasRole('SHELTER')")
    public ResponseEntity<Void> deleteListing(@PathVariable Long listingId) {
        listingService.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }
}
