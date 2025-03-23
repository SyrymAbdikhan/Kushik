package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Application;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByAdopter(User adopter);
    List<Application> findByListing(Listing listing);
    boolean existsByAdopterAndListing(User adopter, Listing listing);

    @Modifying
    @Query("UPDATE Application a SET a.status = 'REJECTED' " +
            "WHERE a.listing = :listing AND a.applicationId != :approvedId")
    void rejectOtherApplications(@Param("listing") Listing listing,
                                 @Param("approvedId") Long approvedId);
}
