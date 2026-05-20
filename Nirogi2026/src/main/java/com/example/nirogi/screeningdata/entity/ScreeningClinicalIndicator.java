package com.example.nirogi.screeningdata.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "screening_clinical_indicator",
        indexes = {

                @Index(
                        name = "idx_indicator_screening",
                        columnList = "screeningRecordId"
                ),

                @Index(
                        name = "idx_indicator_hb",
                        columnList = "hb"
                ),

                @Index(
                        name = "idx_indicator_rbs",
                        columnList = "rbs"
                ),

                @Index(
                        name = "idx_indicator_diagnosis",
                        columnList = "diagnosisCode"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningClinicalIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long screeningRecordId;

    private Long beneficiaryId;

    /*
        LAB / INVESTIGATION
     */

    private Double hb;

    private Double tlc;

    private Double neutrophils;

    private Double lymphocytes;

    private Double monocytes;

    private Double eosinophils;

    private Double basophils;

    private Double packedCellVolume;

    private Double meanCorpuscularVolume;

    private Double mch;

    private Double mchc;

    private Double plateletCount;

    private Double rdwCv;

    private Double rdwSd;

    private Double rbcCount;

    private Double rbs;

    private Double serumCholesterol;

    private Double bloodUrea;

    private Double serumCreatinine;

    private Double tsh;

    private Double psa;
    private String otherDiagnosis;

    /*
        SPECIAL TESTS
     */

    private Boolean urineRoutineExamination;

    private Boolean papSmear;

    /*
        DIAGNOSIS
     */

    private String diagnosisCode;

    /*
        FLAGS
     */

    private Boolean anemiaFlag;

    private Boolean diabetesFlag;

    private Boolean hypertensionFlag;

    private Boolean cardiacDiseaseFlag;

    private Boolean strokeFlag;

    private Boolean tuberculosisFlag;

    private Boolean carcinomaFlag;

    private Boolean malnourishmentFlag;

    private Boolean lowBirthWeightFlag;

    private LocalDateTime createdAt;
}