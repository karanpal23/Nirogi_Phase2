package com.example.nirogi.screeningdata.validator;



import com.example.nirogi.screening.entity.ScreeningRecord;
import com.example.nirogi.screening.repository.ScreeningRecordRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreeningDataValidator {

    private final ScreeningRecordRepository screeningRepo;

    public ScreeningRecord validate(
            String referenceId
    ) {

        return screeningRepo.findByReferenceId(
                referenceId
        ).orElseThrow(() ->
                new RuntimeException(
                        "Invalid reference ID"
                )
        );
    }
}