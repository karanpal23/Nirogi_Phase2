package com.example.nirogi.screening.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabOrderViewDTO {

    private String referenceId;

    private String status;

    private List<LabTestViewDTO> tests;
}
