package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.enumeration.ListingStatus;
import dev.shetel.kushik.model.Location;
import dev.shetel.kushik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByShelter(User shelter);
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByLocation(Location location);
}
