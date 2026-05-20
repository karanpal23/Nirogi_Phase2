package com.example.nirogi.beneficiary.mapper;


import com.example.nirogi.beneficiary.dto.BeneficiaryPayloadDTO;
import com.example.nirogi.beneficiary.entity.Beneficiary;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class BeneficiaryMapper {

    public Beneficiary mapToEntity(
            BeneficiaryPayloadDTO dto
    ) {

        return Beneficiary.builder()

                .memberId(dto.getMemberId())

                .pppId(dto.getPppId())

                .firstName(dto.getFirstName())

                .lastName(dto.getLastName())

                .fatherHusbandFirstname(
                        dto.getFatherHusbandFirstname()
                )

                .fatherHusbandLastname(
                        dto.getFatherHusbandLastname()
                )

                .blockTownCity(
                        dto.getBlockTownCity()
                )

                .wardVillage(
                        dto.getWardVillage()
                )

                .district(
                        dto.getDistrict()
                )

                .districtLgd(
                        dto.getDistrictLgd()
                )

                .blockLgd(
                        dto.getBlockLgd()
                )

                .wardLgd(
                        dto.getWardLgd()
                )

                .income(
                        dto.getIncome()
                )

                .age(
                        dto.getAge()
                )

                .dateOfBirth(
                        parseDate(
                                dto.getDateOfBirth()
                        )
                )

                .gender(
                        dto.getGender()
                )

                .mobileNo(
                        dto.getMobileNo()
                )

                .address(
                        dto.getAddress()
                )

                .createdAt(
                        LocalDateTime.now()
                )

                .updatedAt(
                        LocalDateTime.now()
                )

                .build();
    }


    private LocalDate parseDate(
            String dob
    ) {

        if (dob == null || dob.trim().isEmpty()) {

            return null;
        }

        return LocalDate.parse(dob);
    }
}