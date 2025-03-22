package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.model.enumeration.ListingStatus;
import dev.shetel.kushik.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByLocation(Location location);
    List<Listing> findByTags(Set<Tag> tags);
}
