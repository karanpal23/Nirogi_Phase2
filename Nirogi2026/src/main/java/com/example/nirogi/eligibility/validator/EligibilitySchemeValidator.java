package com.example.nirogi.eligibility.validator;


import com.example.nirogi.eligibility.dto.CreateEligibilitySchemeDTO;

import org.springframework.stereotype.Component;

@Component
public class EligibilitySchemeValidator {

    public void validate(
            CreateEligibilitySchemeDTO request
    ) {

        if (Boolean.TRUE.equals(
                request.getIncomeBased())
        ) {

            if (request.getIncomeLimit() == null) {

                throw new RuntimeException(
                        "Income limit required"
                );
            }
        }

        if ("SENIOR_70".equals(
                request.getSchemeCode())
        ) {

            if (request.getMinAge() == null) {

                throw new RuntimeException(
                        "Minimum age required"
                );
            }
        }
    }
}
