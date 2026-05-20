package com.example.nirogi.screeningdata.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningDataViewDTO {

    private String referenceId;

    private String category;

    private String historySection;

    private String generalExamSection;

    private String systemicSection;

    private String mandatorySection;

    private String diagnosisSection;

    private String prescriptionSection;
}
