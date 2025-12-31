package com.foodreview.global.config;

import com.foodreview.domain.user.entity.Role;
import com.foodreview.domain.user.entity.User;
import com.foodreview.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email:admin@naver.com}")
    private String adminEmail;

    @Value("${admin.password:12345678!}")
    private String adminPassword;

    @Value("${admin.name:관리자}")
    private String adminName;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .name(adminName)
                    .region("서울")
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            log.info("Admin account created: {}", adminEmail);
        } else {
            log.info("Admin account already exists: {}", adminEmail);
        }
    }
}
