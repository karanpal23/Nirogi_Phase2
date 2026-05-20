package com.example.nirogi.screening.controller;

import com.example.nirogi.auth.util.SecurityUtil;
import com.example.nirogi.screening.dto.*;
import com.example.nirogi.screening.service.LabWorkflowService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lab")
@RequiredArgsConstructor
public class LabWorkflowController {

    private final LabWorkflowService service;



    @PostMapping("/order")
    public LabOrderResponseDTO createOrder(
            @RequestBody
            CreateLabOrderDTO request
    ) {

        Long userId =SecurityUtil.getCurrentUserId();;

        return service.createLabOrder(
                request,
                userId
        );
    }

    @GetMapping("/tests/{referenceId}")
    public LabOrderViewDTO getTests(
            @PathVariable
            String referenceId
    ) {

        return service.getLabTests(
                referenceId
        );
    }

    @PostMapping("/results")
    public LabOrderResponseDTO submitResult(
            @RequestBody
            SubmitLabResultsDTO request
    ) {

        Long userId =SecurityUtil.getCurrentUserId();

        return service.submitLabResults(
                request,
                userId
        );
    }
}
