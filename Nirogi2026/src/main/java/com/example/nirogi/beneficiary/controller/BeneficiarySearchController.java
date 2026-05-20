package com.example.nirogi.beneficiary.controller;


import com.example.nirogi.beneficiary.dto.*;
import com.example.nirogi.beneficiary.service.BeneficiarySearchService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beneficiary")
@RequiredArgsConstructor
public class BeneficiarySearchController {

    private final BeneficiarySearchService service;


    @PostMapping("/search")
    public List<BeneficiarySearchResponseDTO>
    search(
            @RequestBody
            SearchBeneficiaryDTO request
    ) {

    	System.out.println("search: "+request.getPppId()+"-"+request.getDistrictId());
        return service.searchBeneficiary(
                request
        );
    }
}
