package com.example.nirogi.beneficiary.dto;


import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiarySearchResponseDTO {

    private Long beneficiaryId;

    private String memberId;

    private String pppId;

    private String firstName;

    private String lastName;
    
    private String fatherHusbandFirstname;

    private LocalDate dateOfBirth;

    private String gender;

    private String mobileNo;

    private String district;

    private Double income;
}