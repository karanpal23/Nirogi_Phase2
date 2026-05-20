package com.example.nirogi.beneficiary.util;


import com.example.nirogi.beneficiary.dto.BeneficiaryPayloadDTO;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class BeneficiaryHashUtil {

    public String generateHash(
            BeneficiaryPayloadDTO dto
    ) {

        try {

            String rawData =

                    safe(dto.getPppId())
                    + "|"

                    + safe(dto.getFirstName())
                    + "|"

                    + safe(dto.getLastName())
                    + "|"

                    + safe(dto.getIncome());

            MessageDigest digest =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            byte[] hashBytes =
                    digest.digest(
                            rawData.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            StringBuilder builder =
                    new StringBuilder();

            for (byte b : hashBytes) {

                builder.append(
                        String.format("%02x", b)
                );
            }

            return builder.toString();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to generate hash"
            );
        }
    }


    private String safe(
            Object value
    ) {

        return value == null
                ? ""
                : value.toString();
    }
}