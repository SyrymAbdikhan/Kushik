package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.request.CreateApplicationRequest;
import dev.shetel.kushik.dto.request.UpdateApplicationRequest;
import dev.shetel.kushik.mapper.ApplicationMapper;
import dev.shetel.kushik.model.Application;
import dev.shetel.kushik.model.Listing;
import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.enumeration.ApplicationStatus;
import dev.shetel.kushik.model.enumeration.ListingStatus;
import dev.shetel.kushik.model.enumeration.UserRole;
import dev.shetel.kushik.repository.ApplicationRepository;
import dev.shetel.kushik.repository.ListingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ListingService listingService;
    private final ListingRepository listingRepository;
    private final UserService userService;
    private final ApplicationMapper applicationMapper;

    @Transactional
    public Application createApplication(CreateApplicationRequest request) {
        User adopter = userService.getCurrentUserEntity();
        if (adopter.getRole() != UserRole.ADOPTER) {
            throw new IllegalArgumentException("Only adopters can apply");
        }

        Listing listing = listingService.getListingById(request.getListingId());
        if (listing.getStatus() != ListingStatus.AVAILABLE) {
            throw new IllegalStateException("Listing is not available for adoption");
        }

        if (applicationRepository.existsByAdopterAndListing(adopter, listing)) {
            throw new IllegalArgumentException("Duplicate application");
        }

        Application application = applicationMapper.toEntity(request, adopter, listing);
        return applicationRepository.save(application);
    }

    @Transactional
    public Application updateApplication(Long applicationId, UpdateApplicationRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        User currentUser = userService.getCurrentUserEntity();
        if (!application.getListing().getShelter().equals(currentUser)) {
            throw new AccessDeniedException("Not authorized to update this application");
        }

        application.setStatus(request.getStatus());
        if (request.getStatus() == ApplicationStatus.APPROVED) {
            Listing listing = application.getListing();
            listing.setStatus(ListingStatus.ADOPTED);
            listingRepository.save(listing);

            applicationRepository.rejectOtherApplications(listing, applicationId);
        }

        return applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<Application> getApplicationsByListing(Long listingId) {
        Listing listing = listingService.getListingById(listingId);
        return applicationRepository.findByListing(listing);
    }

    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Application> getUserApplications() {
        User user = userService.getCurrentUserEntity();
        return applicationRepository.findByAdopter(user);
    }
}
