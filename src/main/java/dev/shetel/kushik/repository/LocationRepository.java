package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByCityAndCountryCode(String city, String countryCode);
}
