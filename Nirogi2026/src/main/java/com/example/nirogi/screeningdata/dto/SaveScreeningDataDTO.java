package com.example.nirogi.screeningdata.dto;


import lombok.Data;

@Data
public class SaveScreeningDataDTO {

    private String referenceId;

    /*
        STRUCTURED JSON VALUES
     */

    private String historySection;

    private String generalExamSection;

    private String systemicSection;

    private String mandatorySection;

    private String diagnosisSection;

    private String prescriptionSection;
}
