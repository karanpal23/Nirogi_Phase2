package com.example.nirogi.beneficiary.service;


import com.example.nirogi.beneficiary.dto.*;

import java.util.List;

public interface BeneficiarySearchService {

    List<BeneficiarySearchResponseDTO>
    searchBeneficiary(
            SearchBeneficiaryDTO request
    );
}
