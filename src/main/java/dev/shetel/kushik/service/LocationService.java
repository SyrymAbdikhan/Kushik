package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.request.CreateLocationRequest;
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
    public Location createLocation(CreateLocationRequest request) {
        if (locationRepository.existsByCityAndCountryCode(
                request.getCity(),
                request.getCountryCode().toUpperCase()
        )) {
            throw new IllegalArgumentException("Location already exists");
        }

        Location location = locationMapper.toEntity(request);
        return locationRepository.save(location);
    }

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    @Transactional
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException("Location not found");
        }
        locationRepository.deleteById(locationId);
    }
}
