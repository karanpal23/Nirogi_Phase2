package com.example.nirogi.reports.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CumulativeReportRowDTO {

    /*
        BASIC INFO
     */

    private Long serialNo;

    private String district;

    private String facilityName;

    private String facilityType;

    /*
        PATIENT VISITS
     */

    private Long cat1;

    private Long cat2;

    private Long cat3;

    private Long cat4;

    private Long cat5;

    private Long cat6;

    private Long totalPatients;

    /*
        LAB INVESTIGATIONS
     */

    private Long hb;

    private Long cbc;

    private Long fbsRbs;

    private Long serumCholesterol;

    private Long bloodUrea;

    private Long serumCreatinine;

    private Long tsh;

    private Long psa;

    private Long papSmear;

    private Long urineRe;

    private Long totalLabInvestigations;

    /*
        DISEASE DETECTED
     */

    private Long anaemia;

    private Long malnourishedChildren;

    private Long lbw;

    private Long tuberculosis;

    private Long carcinoma;

    private Long diabetesMellitus;

    private Long hypertension;

    private Long cardiacDisease;

    private Long stroke;

    private Long others;

    private Long totalDetected;

    private Long referred;
}