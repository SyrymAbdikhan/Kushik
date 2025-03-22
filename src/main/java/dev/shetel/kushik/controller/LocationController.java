package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.request.CreateLocationRequest;
import dev.shetel.kushik.dto.response.LocationDto;
import dev.shetel.kushik.mapper.LocationMapper;
import dev.shetel.kushik.model.Location;
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
    private final LocationMapper locationMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationDto> createLocation(
            @Valid @RequestBody CreateLocationRequest request
    ) {
        Location location = locationService.createLocation(request);
        LocationDto locationDto = locationMapper.toDto(location);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationDto);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<Location> locations = locationService.getLocations();
        List<LocationDto> locationDtos = locations.stream()
                .map(locationMapper::toDto).toList();
        return ResponseEntity.ok(locationDtos);
    }

    @DeleteMapping("/{locationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}