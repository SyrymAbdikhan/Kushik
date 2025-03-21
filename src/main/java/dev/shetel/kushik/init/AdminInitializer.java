package dev.shetel.kushik.init;

import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.enumeration.UserRole;
import dev.shetel.kushik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminIfNeeded() {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.info("Admin credentials not configured - setting default credentials");
            adminEmail = "admin@shetel.dev";
            adminPassword = "admin";
        }

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .username("admin")
                    .email(adminEmail)
                    .passwordHash(passwordEncoder.encode(adminPassword))
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(admin);
            log.info("Created initial admin user email=\"" + adminEmail + "\" and password=\"" + adminPassword + "\"");
        } else {
            log.info("Admin user already exists - skipping");
        }
    }
}
