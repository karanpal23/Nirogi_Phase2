package com.example.nirogi.screeningdata.service;


import com.example.nirogi.screeningdata.dto.*;

public interface ScreeningDataService {

    ScreeningDataResponseDTO saveScreeningData(
            SaveScreeningDataDTO request
    );

    ScreeningDataViewDTO getScreeningData(
            String referenceId
    );
}