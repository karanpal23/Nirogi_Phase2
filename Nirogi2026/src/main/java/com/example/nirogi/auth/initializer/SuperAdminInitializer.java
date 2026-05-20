package com.example.nirogi.auth.initializer;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.nirogi.user.entity.User;
import com.example.nirogi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer
        implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            String username = "SUPERADMIN_system@00001";

            User user = User.builder()
                    .username(username)
                    .passwordHash(encoder.encode(username))
                    .role("SUPERADMIN")
                    .firstName("System")
                    .lastName("Administrator")
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);
        }
    }
}