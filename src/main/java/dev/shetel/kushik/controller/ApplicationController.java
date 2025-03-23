package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.request.CreateApplicationRequest;
import dev.shetel.kushik.dto.request.UpdateApplicationRequest;
import dev.shetel.kushik.dto.response.ApplicationDto;
import dev.shetel.kushik.mapper.ApplicationMapper;
import dev.shetel.kushik.model.Application;
import dev.shetel.kushik.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADOPTER')")
    public ResponseEntity<ApplicationDto> createApplication(
            @Valid @RequestBody CreateApplicationRequest request
    ) {
        Application application = applicationService.createApplication(request);
        ApplicationDto applicationDto = applicationMapper.toDto(application);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationDto);
    }

    @PutMapping("/{applicationId}")
    @PreAuthorize("hasAnyRole('SHELTER', 'ADMIN')")
    public ResponseEntity<ApplicationDto> updateApplication(
            @PathVariable Long applicationId,
            @Valid @RequestBody UpdateApplicationRequest request
    ) {
        Application application = applicationService.updateApplication(applicationId, request);
        ApplicationDto applicationDto = applicationMapper.toDto(application);
        return ResponseEntity.ok(applicationDto);
    }

    @GetMapping("/listing/{listingId}")
    @PreAuthorize("hasAnyRole('SHELTER', 'ADMIN')")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByListing(
            @PathVariable Long listingId
    ) {
        List<Application> applications = applicationService.getApplicationsByListing(listingId);
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto).toList();
        return ResponseEntity.ok(applicationDtos);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ApplicationDto>> getApplications() {
        List<Application> applications = applicationService.getApplications();
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto).toList();
        return ResponseEntity.ok(applicationDtos);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADOPTER')")
    public ResponseEntity<List<ApplicationDto>> getUserApplications() {
        List<Application> applications = applicationService.getUserApplications();
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto).toList();
        return ResponseEntity.ok(applicationDtos);
    }
}
