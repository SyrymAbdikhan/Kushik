package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.model.enumeration.ListingStatus;
import dev.shetel.kushik.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByLocation(Location location);

    @Query("SELECT DISTINCT l FROM Listing l " +
            "JOIN l.tags t " +
            "WHERE t IN :tags " +
            "GROUP BY l " +
            "HAVING COUNT(t) = :tagCount")
    List<Listing> findByTagsIn(@Param("tags") Set<Tag> tags,
                               @Param("tagCount") long tagCount);

    default List<Listing> findByTagsIn(Set<Tag> tags) {
        return findByTagsIn(tags, tags.size());
    }
}
