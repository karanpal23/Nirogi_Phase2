package com.example.nirogi.beneficiary.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchBeneficiaryDTO {

    /*
        OPTIONAL
     */

    private String districtId;

    /*
        SEARCH OPTION 1
     */

    private String pppId;

    /*
        SEARCH OPTION 2
     */

    private String firstName;

    private String mobileNo;
}
