package com.example.nirogi.screening.util;

import org.springframework.stereotype.Component;

@Component
public class ReferenceIdGenerator {

    public String generate() {

        return "SCR-" +
                System.currentTimeMillis();
    }
}
