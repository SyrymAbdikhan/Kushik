package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.CreateLocationRequest;
import dev.shetel.kushik.dto.LocationDto;
import dev.shetel.kushik.mapper.LocationMapper;
import dev.shetel.kushik.model.Location;
import dev.shetel.kushik.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public LocationDto createLocation(CreateLocationRequest request) {
        if (locationRepository.existsByCityAndCountryCode(
                request.getCity(),
                request.getCountryCode().toUpperCase()
        )) {
            throw new IllegalArgumentException("Location already exists");
        }

        Location location = locationMapper.toEntity(request);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toDto(savedLocation);
    }

    public List<LocationDto> getLocations() {
        return locationRepository.findAll()
                .stream()
                .map(locationMapper::toDto)
                .toList();
    }

    public LocationDto getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        return locationMapper.toDto(location);
    }

    @Transactional
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException("Location not found");
        }
        locationRepository.deleteById(locationId);
    }
}
