package com.example.nirogi.screening.service;


import com.example.nirogi.screening.dto.*;

public interface ScreeningService {

    ScreeningResponseDTO createScreening(
            CreateScreeningDTO request,
            Long doctorId
    );

    ScreeningResponseDTO submitLabResult(
            LabResultDTO request,
            Long userId
    );
}