package com.example.nirogi.screeningdata.mapper;



import com.example.nirogi.screening.entity.ScreeningRecord;

import com.example.nirogi.screeningdata.dto.*;
import com.example.nirogi.screeningdata.entity.*;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScreeningDataMapper {

    /*
    ============================================
                CREATE METHODS
    ============================================
     */

    public ScreeningCat1 mapCat1(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        return ScreeningCat1.builder()
                .screeningRecordId(screening.getId())
                .historySection(request.getHistorySection())
                .generalExamSection(request.getGeneralExamSection())
                .systemicSection(request.getSystemicSection())
                .mandatorySection(request.getMandatorySection())
                .diagnosisSection(request.getDiagnosisSection())
                .prescriptionSection(request.getPrescriptionSection())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public ScreeningCat2 mapCat2(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        return ScreeningCat2.builder()
                .screeningRecordId(screening.getId())
                .historySection(request.getHistorySection())
                .generalExamSection(request.getGeneralExamSection())
                .systemicSection(request.getSystemicSection())
                .mandatorySection(request.getMandatorySection())
                .diagnosisSection(request.getDiagnosisSection())
                .prescriptionSection(request.getPrescriptionSection())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public ScreeningCat3 mapCat3(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        return ScreeningCat3.builder()
                .screeningRecordId(screening.getId())
                .historySection(request.getHistorySection())
                .generalExamSection(request.getGeneralExamSection())
                .systemicSection(request.getSystemicSection())
                .mandatorySection(request.getMandatorySection())
                .diagnosisSection(request.getDiagnosisSection())
                .prescriptionSection(request.getPrescriptionSection())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public ScreeningCat4 mapCat4(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        return ScreeningCat4.builder()
                .screeningRecordId(screening.getId())
                .historySection(request.getHistorySection())
                .generalExamSection(request.getGeneralExamSection())
                .systemicSection(request.getSystemicSection())
                .mandatorySection(request.getMandatorySection())
                .diagnosisSection(request.getDiagnosisSection())
                .prescriptionSection(request.getPrescriptionSection())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public ScreeningCat5 mapCat5(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        return ScreeningCat5.builder()
                .screeningRecordId(screening.getId())
                .historySection(request.getHistorySection())
                .generalExamSection(request.getGeneralExamSection())
                .systemicSection(request.getSystemicSection())
                .mandatorySection(request.getMandatorySection())
                .diagnosisSection(request.getDiagnosisSection())
                .prescriptionSection(request.getPrescriptionSection())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public ScreeningCat6 mapCat6(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        return ScreeningCat6.builder()
                .screeningRecordId(screening.getId())
                .historySection(request.getHistorySection())
                .generalExamSection(request.getGeneralExamSection())
                .systemicSection(request.getSystemicSection())
                .mandatorySection(request.getMandatorySection())
                .diagnosisSection(request.getDiagnosisSection())
                .prescriptionSection(request.getPrescriptionSection())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    /*
    ============================================
                UPDATE METHODS
    ============================================
     */

    public void updateCat1(
            ScreeningCat1 entity,
            SaveScreeningDataDTO request
    ) {

        updateCommonFields(
                entity,
                request
        );
    }


    public void updateCat2(
            ScreeningCat2 entity,
            SaveScreeningDataDTO request
    ) {

        updateCommonFields(
                entity,
                request
        );
    }


    public void updateCat3(
            ScreeningCat3 entity,
            SaveScreeningDataDTO request
    ) {

        updateCommonFields(
                entity,
                request
        );
    }


    public void updateCat4(
            ScreeningCat4 entity,
            SaveScreeningDataDTO request
    ) {

        updateCommonFields(
                entity,
                request
        );
    }


    public void updateCat5(
            ScreeningCat5 entity,
            SaveScreeningDataDTO request
    ) {

        updateCommonFields(
                entity,
                request
        );
    }


    public void updateCat6(
            ScreeningCat6 entity,
            SaveScreeningDataDTO request
    ) {

        updateCommonFields(
                entity,
                request
        );
    }


    /*
    ============================================
            COMMON UPDATE METHODS
    ============================================
     */

    private void updateCommonFields(
            Object entity,
            SaveScreeningDataDTO request
    ) {

        if (entity instanceof ScreeningCat1) {

            ScreeningCat1 e =
                    (ScreeningCat1) entity;

            setCommonValues(
                    e,
                    request
            );

        } else if (entity instanceof ScreeningCat2) {

            ScreeningCat2 e =
                    (ScreeningCat2) entity;

            setCommonValues(
                    e,
                    request
            );

        } else if (entity instanceof ScreeningCat3) {

            ScreeningCat3 e =
                    (ScreeningCat3) entity;

            setCommonValues(
                    e,
                    request
            );

        } else if (entity instanceof ScreeningCat4) {

            ScreeningCat4 e =
                    (ScreeningCat4) entity;

            setCommonValues(
                    e,
                    request
            );

        } else if (entity instanceof ScreeningCat5) {

            ScreeningCat5 e =
                    (ScreeningCat5) entity;

            setCommonValues(
                    e,
                    request
            );

        } else if (entity instanceof ScreeningCat6) {

            ScreeningCat6 e =
                    (ScreeningCat6) entity;

            setCommonValues(
                    e,
                    request
            );
        }
    }


    /*
    ============================================
            COMMON FIELD SETTER
    ============================================
     */

    private void setCommonValues(
            ScreeningCat1 entity,
            SaveScreeningDataDTO request
    ) {

        entity.setHistorySection(
                request.getHistorySection()
        );

        entity.setGeneralExamSection(
                request.getGeneralExamSection()
        );

        entity.setSystemicSection(
                request.getSystemicSection()
        );

        entity.setMandatorySection(
                request.getMandatorySection()
        );

        entity.setDiagnosisSection(
                request.getDiagnosisSection()
        );

        entity.setPrescriptionSection(
                request.getPrescriptionSection()
        );

        entity.setUpdatedAt(
                LocalDateTime.now()
        );
    }


    private void setCommonValues(
            ScreeningCat2 entity,
            SaveScreeningDataDTO request
    ) {

        entity.setHistorySection(
                request.getHistorySection()
        );

        entity.setGeneralExamSection(
                request.getGeneralExamSection()
        );

        entity.setSystemicSection(
                request.getSystemicSection()
        );

        entity.setMandatorySection(
                request.getMandatorySection()
        );

        entity.setDiagnosisSection(
                request.getDiagnosisSection()
        );

        entity.setPrescriptionSection(
                request.getPrescriptionSection()
        );

        entity.setUpdatedAt(
                LocalDateTime.now()
        );
    }


    private void setCommonValues(
            ScreeningCat3 entity,
            SaveScreeningDataDTO request
    ) {

        entity.setHistorySection(
                request.getHistorySection()
        );

        entity.setGeneralExamSection(
                request.getGeneralExamSection()
        );

        entity.setSystemicSection(
                request.getSystemicSection()
        );

        entity.setMandatorySection(
                request.getMandatorySection()
        );

        entity.setDiagnosisSection(
                request.getDiagnosisSection()
        );

        entity.setPrescriptionSection(
                request.getPrescriptionSection()
        );

        entity.setUpdatedAt(
                LocalDateTime.now()
        );
    }


    private void setCommonValues(
            ScreeningCat4 entity,
            SaveScreeningDataDTO request
    ) {

        entity.setHistorySection(
                request.getHistorySection()
        );

        entity.setGeneralExamSection(
                request.getGeneralExamSection()
        );

        entity.setSystemicSection(
                request.getSystemicSection()
        );

        entity.setMandatorySection(
                request.getMandatorySection()
        );

        entity.setDiagnosisSection(
                request.getDiagnosisSection()
        );

        entity.setPrescriptionSection(
                request.getPrescriptionSection()
        );

        entity.setUpdatedAt(
                LocalDateTime.now()
        );
    }


    private void setCommonValues(
            ScreeningCat5 entity,
            SaveScreeningDataDTO request
    ) {

        entity.setHistorySection(
                request.getHistorySection()
        );

        entity.setGeneralExamSection(
                request.getGeneralExamSection()
        );

        entity.setSystemicSection(
                request.getSystemicSection()
        );

        entity.setMandatorySection(
                request.getMandatorySection()
        );

        entity.setDiagnosisSection(
                request.getDiagnosisSection()
        );

        entity.setPrescriptionSection(
                request.getPrescriptionSection()
        );

        entity.setUpdatedAt(
                LocalDateTime.now()
        );
    }


    private void setCommonValues(
            ScreeningCat6 entity,
            SaveScreeningDataDTO request
    ) {

        entity.setHistorySection(
                request.getHistorySection()
        );

        entity.setGeneralExamSection(
                request.getGeneralExamSection()
        );

        entity.setSystemicSection(
                request.getSystemicSection()
        );

        entity.setMandatorySection(
                request.getMandatorySection()
        );

        entity.setDiagnosisSection(
                request.getDiagnosisSection()
        );

        entity.setPrescriptionSection(
                request.getPrescriptionSection()
        );

        entity.setUpdatedAt(
                LocalDateTime.now()
        );
    }


    /*
    ============================================
                VIEW RESPONSE
    ============================================
     */

    public ScreeningDataViewDTO mapViewResponse(
            ScreeningRecord screening,
            String history,
            String general,
            String systemic,
            String mandatory,
            String diagnosis,
            String prescription
    ) {

        return ScreeningDataViewDTO
                .builder()

                .referenceId(
                        screening.getReferenceId()
                )

                .category(
                        screening.getScreeningCategory()
                                .name()
                )

                .historySection(
                        history
                )

                .generalExamSection(
                        general
                )

                .systemicSection(
                        systemic
                )

                .mandatorySection(
                        mandatory
                )

                .diagnosisSection(
                        diagnosis
                )

                .prescriptionSection(
                        prescription
                )

                .build();
    }
}