package com.example.nirogi.screening.controller;


import com.example.nirogi.auth.util.SecurityUtil;
import com.example.nirogi.screening.dto.*;
import com.example.nirogi.screening.service.ScreeningService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screening")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService service;


    @PostMapping("/create")
    public ScreeningResponseDTO createScreening(
            @RequestBody CreateScreeningDTO request
    ) {

        Long doctorId =
                SecurityUtil.getCurrentUserId();

        return service.createScreening(
                request,
                doctorId
        );
    }


    @PostMapping("/lab-result")
    public ScreeningResponseDTO submitLabResult(
            @RequestBody LabResultDTO request
    ) {

        Long userId =
                SecurityUtil.getCurrentUserId();

        return service.submitLabResult(
                request,
                userId
        );
    }
}
