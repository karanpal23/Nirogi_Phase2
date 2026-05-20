package com.example.nirogi.screeningdata.util;


import com.example.nirogi.screening.entity.ScreeningRecord;
import com.example.nirogi.screeningdata.entity.ScreeningClinicalIndicator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClinicalIndicatorExtractor {

    private final ObjectMapper mapper =
            new ObjectMapper();


    public ScreeningClinicalIndicator extract(
            ScreeningRecord screening,
            String mandatoryJson,
            String diagnosisJson
    ) {

        try {

            JsonNode mandatory =
                    mapper.readTree(
                            mandatoryJson
                    );

            JsonNode diagnosis =
                    mapper.readTree(
                            diagnosisJson
                    );

            String diagnosisCode =
                    diagnosis.path("diagnosis")
                            .asText("NONE");
            String otherDiagnosis =
                    diagnosis.path("otherDiagnosis")
                            .asText(null);

            return ScreeningClinicalIndicator
                    .builder()

                    .screeningRecordId(
                            screening.getId()
                    )

                    .beneficiaryId(
                            screening.getBeneficiaryId()
                    )

                    .hb(
                            getDouble(
                                    mandatory,
                                    "hb"
                            )
                    )

                    .tlc(
                            getDouble(
                                    mandatory,
                                    "tlc"
                            )
                    )

                    .rbs(
                            getDouble(
                                    mandatory,
                                    "rbs"
                            )
                    )

                    .serumCholesterol(
                            getDouble(
                                    mandatory,
                                    "serumCholesterol"
                            )
                    )

                    .bloodUrea(
                            getDouble(
                                    mandatory,
                                    "bloodUrea"
                            )
                    )

                    .serumCreatinine(
                            getDouble(
                                    mandatory,
                                    "serumCreatinine"
                            )
                    )

                    .tsh(
                            getDouble(
                                    mandatory,
                                    "tsh"
                            )
                    )

                    .diagnosisCode(
                            diagnosisCode
                    )

                    /*
                        FLAGS
                     */

                    .anemiaFlag(
                            "ANEMIA".equals(
                                    diagnosisCode
                            )
                    )

                    .diabetesFlag(
                            "DIABETES_MELLITUS"
                                    .equals(
                                            diagnosisCode
                                    )
                    )

                    .hypertensionFlag(
                            "HYPERTENSION"
                                    .equals(
                                            diagnosisCode
                                    )
                    )

                    .cardiacDiseaseFlag(
                            "CARDIAC_DISEASE"
                                    .equals(
                                            diagnosisCode
                                    )
                    )
                    .otherDiagnosis(
                            otherDiagnosis
                    )

                    .strokeFlag(
                            "STROKE"
                                    .equals(
                                            diagnosisCode
                                    )
                    )

                    .createdAt(
                            LocalDateTime.now()
                    )

                    .build();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to extract indicators"
            );
        }
    }


    private Double getDouble(
            JsonNode node,
            String field
    ) {

        if (!node.has(field)) {

            return null;
        }

        return node.get(field)
                .asDouble();
    }
}
