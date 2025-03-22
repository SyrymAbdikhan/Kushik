package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.request.CreateLocationRequest;
import dev.shetel.kushik.dto.response.LocationDto;
import dev.shetel.kushik.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .locationId(location.getLocationId())
                .city(location.getCity())
                .countryCode(location.getCountryCode())
                .build();
    }

    public Location toEntity(CreateLocationRequest request) {
        return Location.builder()
                .city(request.getCity())
                .countryCode(request.getCountryCode().toUpperCase())
                .build();
    }
}
