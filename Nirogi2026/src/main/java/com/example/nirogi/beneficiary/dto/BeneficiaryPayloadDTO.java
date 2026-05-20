package com.example.nirogi.beneficiary.dto;

import lombok.Data;



@Data
public class BeneficiaryPayloadDTO {

    private String memberId;

    private String pppId;

    private String firstName;

    private String lastName;

    private String fatherHusbandFirstname;

    private String fatherHusbandLastname;

    private String blockTownCity;

    private String wardVillage;

    private String district;

    private String districtLgd;

    private String blockLgd;

    private String wardLgd;

    private Double income;

    private Integer age;

    private String dateOfBirth;

    private String gender;

    private String mobileNo;

    private String address;
}
