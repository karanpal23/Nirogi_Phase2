package com.example.nirogi.beneficiary.rules;


import com.example.nirogi.beneficiary.entity.BeneficiarySnapshot;

import org.springframework.stereotype.Component;

@Component
public class BeneficiaryComparisonRule {

    public boolean requiresNewVersion(
            BeneficiarySnapshot latest,
            String newHash
    ) {

        return !latest.getRecordHash()
                .equals(newHash);
    }
}
