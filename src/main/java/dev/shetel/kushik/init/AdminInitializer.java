package dev.shetel.kushik.init;

import dev.shetel.kushik.model.User;
import dev.shetel.kushik.model.UserRole;
import dev.shetel.kushik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminIfNeeded() {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            System.out.println("Admin credentials not configured - skipping admin creation");
            return;
        }

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .email(adminEmail)
                    .passwordHash(passwordEncoder.encode(adminPassword))
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("Created initial admin user with email: " + adminEmail);
        }
    }
}
