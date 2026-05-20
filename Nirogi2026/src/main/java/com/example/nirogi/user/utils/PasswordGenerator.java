package com.example.nirogi.user.utils;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

    public String generateInitialPassword(String username) {

        return username;
    }
}
