package com.example.nirogi.eligibility.controller;


import com.example.nirogi.auth.util.SecurityUtil;
import com.example.nirogi.eligibility.dto.*;
import com.example.nirogi.eligibility.service.EligibilityService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eligibility")
@RequiredArgsConstructor
public class EligibilityController {

    private final EligibilityService service;


    @PostMapping("/scheme")
    public EligibilitySchemeResponseDTO createScheme(
            @RequestBody CreateEligibilitySchemeDTO request
    ) {

        Long adminId =
                SecurityUtil.getCurrentUserId();

        return service.createScheme(
                request,
                adminId
        );
    }


    @GetMapping("/scheme/active")
    public List<EligibilitySchemeResponseDTO>
    getActiveSchemes() {

        return service.getActiveSchemes();
    }


    @GetMapping("/check/{memberId}")
    public EligibilityResponseDTO checkEligibility(
            @PathVariable String memberId
    ) {

        return service.checkEligibility(
                memberId
        );
    }
}