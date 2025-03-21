package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Application;
import dev.shetel.kushik.model.ApplicationStatus;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByAdopter(User adopter);
    List<Application> findByListing(Listing listing);
    List<Application> findByStatus(ApplicationStatus status);
    boolean existsByAdopterAndListing(User adopter, Listing listing);
}
