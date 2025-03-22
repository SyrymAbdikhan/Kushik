package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.CreateLocationRequest;
import dev.shetel.kushik.dto.LocationDto;
import dev.shetel.kushik.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationDto> createLocation(
            @Valid @RequestBody CreateLocationRequest request
    ) {
        LocationDto location = locationService.createLocation(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(location);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @DeleteMapping("/{locationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}