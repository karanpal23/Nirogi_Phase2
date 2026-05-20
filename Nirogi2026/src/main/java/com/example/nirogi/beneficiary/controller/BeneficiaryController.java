package com.example.nirogi.beneficiary.controller;


import com.example.nirogi.auth.util.SecurityUtil;
import com.example.nirogi.beneficiary.dto.BeneficiarySyncRequestDTO;
import com.example.nirogi.beneficiary.dto.BeneficiarySyncResponseDTO;
import com.example.nirogi.beneficiary.service.BeneficiaryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beneficiary")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService service;

    @PostMapping("/sync")
    public BeneficiarySyncResponseDTO syncBeneficiaries(
            @RequestBody BeneficiarySyncRequestDTO request
    ) {

        Long userId =
                SecurityUtil.getCurrentUserId();

        return service.syncBeneficiaries(
                request,
                userId
        );
    }
}