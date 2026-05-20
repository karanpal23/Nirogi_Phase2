package com.example.nirogi.user.utils;

import org.springframework.stereotype.Component;

import com.example.nirogi.user.enums.UserRole;

@Component
public class UsernameGenerator {

    public String generate(
            String role,
            String firstName,
            Long sequence
    ) {

        String cleanFirstName =
                firstName
                        .trim()
                        .toLowerCase()
                        .replaceAll("[^a-z]", "");

        return role.toUpperCase()
                + "_"
                + cleanFirstName
                + "@"
                + String.format("%05d", sequence);
    }
}
