package com.example.nirogi.screening.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitLabResultsDTO {

    private String referenceId;

    private List<LabResultItemDTO> results;
}
