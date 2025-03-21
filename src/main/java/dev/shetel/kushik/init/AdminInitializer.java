package dev.shetel.kushik.init;

import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.UserRole;
import dev.shetel.kushik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminIfNeeded() {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            logger.info("Admin credentials not configured - setting default credentials");
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
            logger.info("Created initial admin user email=\"" + adminEmail + "\" and password=\"" + adminPassword + "\"");
        } else {
            logger.info("Admin user already exists - skipping");
        }
    }
}
