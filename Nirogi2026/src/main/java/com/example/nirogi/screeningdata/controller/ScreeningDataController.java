package com.example.nirogi.screeningdata.controller;

import com.example.nirogi.screeningdata.dto.*;
import com.example.nirogi.screeningdata.service.ScreeningDataService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screening-data")
@RequiredArgsConstructor
public class ScreeningDataController {

    private final ScreeningDataService service;


    @PostMapping("/save")
    public ScreeningDataResponseDTO saveScreeningData(
            @RequestBody SaveScreeningDataDTO request
    ) {

        return service.saveScreeningData(
                request
        );
    }


    @GetMapping("/{referenceId}")
    public ScreeningDataViewDTO getScreeningData(
            @PathVariable String referenceId
    ) {

        return service.getScreeningData(
                referenceId
        );
    }
}