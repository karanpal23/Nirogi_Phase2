package com.example.nirogi.eligibility.rules;


import com.example.nirogi.beneficiary.entity.Beneficiary;
import com.example.nirogi.beneficiary.entity.BeneficiarySnapshot;
import com.example.nirogi.beneficiary.enums.BeneficiaryStatus;

import com.example.nirogi.eligibility.dto.EligibilityResponseDTO;
import com.example.nirogi.eligibility.entity.EligibilityScheme;
import com.example.nirogi.eligibility.enums.SchemeCode;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class EligibilityRuleEngine {

    public EligibilityResponseDTO evaluate(
            Beneficiary beneficiary,
            BeneficiarySnapshot snapshot,
            List<EligibilityScheme> schemes
    ) {

        if (snapshot.getBeneficiaryStatus()
                == BeneficiaryStatus.REMOVED) {

            return EligibilityResponseDTO.builder()
                    .memberId(
                            beneficiary.getMemberId()
                    )
                    .eligible(false)
                    .reason("Beneficiary removed")
                    .build();
        }

        int age =
                calculateAge(
                        beneficiary.getDateOfBirth()
                );

        for (EligibilityScheme scheme : schemes) {

            /*
                income based
             */
            if (scheme.getSchemeCode()
                    == SchemeCode.INCOME) {

                if (snapshot.getIncome()
                        <= scheme.getIncomeLimit()) {

                    return EligibilityResponseDTO
                            .builder()
                            .memberId(
                                    beneficiary.getMemberId()
                            )
                            .eligible(true)
                            .eligibleScheme(
                                    scheme.getSchemeCode().name()
                            )
                            .income(
                                    snapshot.getIncome()
                            )
                            .age(age)
                            .reason(
                                    "Income within slab"
                            )
                            .build();
                }
            }

            /*
                senior citizen scheme
             */
            if (scheme.getSchemeCode()
                    == SchemeCode.SENIOR_70) {

                if (age >= scheme.getMinAge()) {

                    return EligibilityResponseDTO
                            .builder()
                            .memberId(
                                    beneficiary.getMemberId()
                            )
                            .eligible(true)
                            .eligibleScheme(
                                    scheme.getSchemeCode().name()
                            )
                            .income(
                                    snapshot.getIncome()
                            )
                            .age(age)
                            .reason(
                                    "Age above 70"
                            )
                            .build();
                }
            }
        }

        return EligibilityResponseDTO.builder()
                .memberId(
                        beneficiary.getMemberId()
                )
                .eligible(false)
                .income(
                        snapshot.getIncome()
                )
                .age(age)
                .reason(
                        "No matching scheme"
                )
                .build();
    }


    private int calculateAge(
            LocalDate dob
    ) {

        return Period.between(
                dob,
                LocalDate.now()
        ).getYears();
    }
}