package com.example.nirogi.eligibility.service;


import com.example.nirogi.eligibility.dto.*;

import java.util.List;

public interface EligibilityService {

    EligibilitySchemeResponseDTO createScheme(
            CreateEligibilitySchemeDTO request,
            Long adminId
    );

    List<EligibilitySchemeResponseDTO>
    getActiveSchemes();

    EligibilityResponseDTO checkEligibility(
            String memberId
    );
}
