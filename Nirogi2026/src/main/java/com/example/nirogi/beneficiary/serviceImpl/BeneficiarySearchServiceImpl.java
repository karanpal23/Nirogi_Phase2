package com.example.nirogi.beneficiary.serviceImpl;


import com.example.nirogi.beneficiary.dto.*;
import com.example.nirogi.beneficiary.entity.Beneficiary;
import com.example.nirogi.beneficiary.repository.BeneficiarySearchRepository;
import com.example.nirogi.beneficiary.service.BeneficiarySearchService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeneficiarySearchServiceImpl
        implements BeneficiarySearchService {

    private final BeneficiarySearchRepository repository;


    @Override
    public List<BeneficiarySearchResponseDTO>
    searchBeneficiary(
            SearchBeneficiaryDTO request
    ) {

        List<Beneficiary> beneficiaries;

        /*
            SEARCH BY PPP ID
         */

        if (request.getPppId() != null
                && !request.getPppId().isEmpty()) {

            beneficiaries =
                    repository.searchByPppId(

                            request.getDistrictId(),

                            request.getPppId()
                    );

        }

        /*
            SEARCH BY NAME + MOBILE
         */

        else {

            beneficiaries =
                    repository.searchByNameAndMobile(

                            request.getDistrictId(),

                            request.getFirstName(),

                            request.getMobileNo()
                    );
        }

        return beneficiaries.stream()

                .map(this::mapResponse)

                .collect(Collectors.toList());
    }


    private BeneficiarySearchResponseDTO
    mapResponse(
            Beneficiary b
    ) {

        return BeneficiarySearchResponseDTO
                .builder()

                .beneficiaryId(
                        b.getId()
                )

                .memberId(
                        b.getMemberId()
                )

                .pppId(
                        b.getPppId()
                )

                .firstName(
                        b.getFirstName()
                )

                .lastName(
                        b.getLastName()
                )
                .fatherHusbandFirstname(
                		b.getFatherHusbandFirstname()
                		)

                .dateOfBirth(
                        b.getDateOfBirth()
                )

                .gender(
                        b.getGender()
                )

                .mobileNo(
                        b.getMobileNo()
                )

                .district(
                        String.valueOf(
                                b.getDistrict()
                        )
                )
                .income(
                		b.getIncome()
                		)

              

                .build();
    }
}
