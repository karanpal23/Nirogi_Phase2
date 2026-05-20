package com.example.nirogi.screeningdata.serviceImpl;



import com.example.nirogi.screening.entity.ScreeningRecord;
import com.example.nirogi.screening.enums.ScreeningCategory;

import com.example.nirogi.screeningdata.dto.*;
import com.example.nirogi.screeningdata.entity.*;
import com.example.nirogi.screeningdata.mapper.ScreeningDataMapper;
import com.example.nirogi.screeningdata.repository.*;
import com.example.nirogi.screeningdata.service.ScreeningDataService;
import com.example.nirogi.screeningdata.util.ClinicalIndicatorExtractor;
import com.example.nirogi.screeningdata.validator.ScreeningDataValidator;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningDataServiceImpl
        implements ScreeningDataService {

    private final ScreeningDataValidator validator;

    private final ScreeningDataMapper mapper;

    private final ScreeningCat1Repository cat1Repo;
    private final ScreeningCat2Repository cat2Repo;
    private final ScreeningCat3Repository cat3Repo;
    private final ScreeningCat4Repository cat4Repo;
    private final ScreeningCat5Repository cat5Repo;
    private final ScreeningCat6Repository cat6Repo;

    private final ScreeningClinicalIndicatorRepository indicatorRepo;

    private final ClinicalIndicatorExtractor extractor;


    @Override
    public ScreeningDataResponseDTO saveScreeningData(
            SaveScreeningDataDTO request
    ) {

        ScreeningRecord screening =
                validator.validate(
                        request.getReferenceId()
                );

        /*
            CATEGORY DATA UPSERT
         */

        switch (screening.getScreeningCategory()) {

            case CAT1:

                upsertCat1(
                        screening,
                        request
                );

                break;

            case CAT2:

                upsertCat2(
                        screening,
                        request
                );

                break;

            case CAT3:

                upsertCat3(
                        screening,
                        request
                );

                break;

            case CAT4:

                upsertCat4(
                        screening,
                        request
                );

                break;

            case CAT5:

                upsertCat5(
                        screening,
                        request
                );

                break;

            case CAT6:

                upsertCat6(
                        screening,
                        request
                );

                break;
        }

        /*
            CLINICAL INDICATOR UPSERT
         */

        ScreeningClinicalIndicator extracted =
                extractor.extract(
                        screening,
                        request.getMandatorySection(),
                        request.getDiagnosisSection()
                );

        Optional<ScreeningClinicalIndicator> existingIndicator =
                indicatorRepo.findByScreeningRecordId(
                        screening.getId()
                );

        if (existingIndicator.isPresent()) {

            ScreeningClinicalIndicator existing =
                    existingIndicator.get();

            updateIndicator(
                    existing,
                    extracted
            );

            indicatorRepo.save(existing);

        } else {

            indicatorRepo.save(extracted);
        }

        return ScreeningDataResponseDTO
                .builder()

                .referenceId(
                        request.getReferenceId()
                )

                .message(
                        "Screening data saved successfully"
                )

                .build();
    }


    /*
    ============================================
                CATEGORY UPSERTS
    ============================================
     */

    private void upsertCat1(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        Optional<ScreeningCat1> existing =
                cat1Repo.findByScreeningRecordId(
                        screening.getId()
                );

        ScreeningCat1 entity;

        if (existing.isPresent()) {

            entity = existing.get();

            mapper.updateCat1(
                    entity,
                    request
            );

        } else {

            entity = mapper.mapCat1(
                    screening,
                    request
            );
        }

        cat1Repo.save(entity);
    }


    private void upsertCat2(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        Optional<ScreeningCat2> existing =
                cat2Repo.findByScreeningRecordId(
                        screening.getId()
                );

        ScreeningCat2 entity;

        if (existing.isPresent()) {

            entity = existing.get();

            mapper.updateCat2(
                    entity,
                    request
            );

        } else {

            entity = mapper.mapCat2(
                    screening,
                    request
            );
        }

        cat2Repo.save(entity);
    }


    private void upsertCat3(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        Optional<ScreeningCat3> existing =
                cat3Repo.findByScreeningRecordId(
                        screening.getId()
                );

        ScreeningCat3 entity;

        if (existing.isPresent()) {

            entity = existing.get();

            mapper.updateCat3(
                    entity,
                    request
            );

        } else {

            entity = mapper.mapCat3(
                    screening,
                    request
            );
        }

        cat3Repo.save(entity);
    }


    private void upsertCat4(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        Optional<ScreeningCat4> existing =
                cat4Repo.findByScreeningRecordId(
                        screening.getId()
                );

        ScreeningCat4 entity;

        if (existing.isPresent()) {

            entity = existing.get();

            mapper.updateCat4(
                    entity,
                    request
            );

        } else {

            entity = mapper.mapCat4(
                    screening,
                    request
            );
        }

        cat4Repo.save(entity);
    }


    private void upsertCat5(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        Optional<ScreeningCat5> existing =
                cat5Repo.findByScreeningRecordId(
                        screening.getId()
                );

        ScreeningCat5 entity;

        if (existing.isPresent()) {

            entity = existing.get();

            mapper.updateCat5(
                    entity,
                    request
            );

        } else {

            entity = mapper.mapCat5(
                    screening,
                    request
            );
        }

        cat5Repo.save(entity);
    }


    private void upsertCat6(
            ScreeningRecord screening,
            SaveScreeningDataDTO request
    ) {

        Optional<ScreeningCat6> existing =
                cat6Repo.findByScreeningRecordId(
                        screening.getId()
                );

        ScreeningCat6 entity;

        if (existing.isPresent()) {

            entity = existing.get();

            mapper.updateCat6(
                    entity,
                    request
            );

        } else {

            entity = mapper.mapCat6(
                    screening,
                    request
            );
        }

        cat6Repo.save(entity);
    }


    /*
    ============================================
            INDICATOR UPDATE
    ============================================
     */

    private void updateIndicator(
            ScreeningClinicalIndicator existing,
            ScreeningClinicalIndicator extracted
    ) {

        existing.setHb(
                extracted.getHb()
        );

        existing.setTlc(
                extracted.getTlc()
        );

        existing.setNeutrophils(
                extracted.getNeutrophils()
        );

        existing.setLymphocytes(
                extracted.getLymphocytes()
        );

        existing.setMonocytes(
                extracted.getMonocytes()
        );

        existing.setEosinophils(
                extracted.getEosinophils()
        );

        existing.setBasophils(
                extracted.getBasophils()
        );

        existing.setPackedCellVolume(
                extracted.getPackedCellVolume()
        );

        existing.setMeanCorpuscularVolume(
                extracted.getMeanCorpuscularVolume()
        );

        existing.setMch(
                extracted.getMch()
        );

        existing.setMchc(
                extracted.getMchc()
        );

        existing.setPlateletCount(
                extracted.getPlateletCount()
        );

        existing.setRdwCv(
                extracted.getRdwCv()
        );

        existing.setRdwSd(
                extracted.getRdwSd()
        );

        existing.setRbcCount(
                extracted.getRbcCount()
        );

        existing.setRbs(
                extracted.getRbs()
        );

        existing.setSerumCholesterol(
                extracted.getSerumCholesterol()
        );

        existing.setBloodUrea(
                extracted.getBloodUrea()
        );

        existing.setSerumCreatinine(
                extracted.getSerumCreatinine()
        );

        existing.setTsh(
                extracted.getTsh()
        );

        existing.setPsa(
                extracted.getPsa()
        );

        existing.setUrineRoutineExamination(
                extracted.getUrineRoutineExamination()
        );

        existing.setPapSmear(
                extracted.getPapSmear()
        );

        existing.setDiagnosisCode(
                extracted.getDiagnosisCode()
        );

        existing.setOtherDiagnosis(
                extracted.getOtherDiagnosis()
        );

        existing.setAnemiaFlag(
                extracted.getAnemiaFlag()
        );

        existing.setDiabetesFlag(
                extracted.getDiabetesFlag()
        );

        existing.setHypertensionFlag(
                extracted.getHypertensionFlag()
        );

        existing.setCardiacDiseaseFlag(
                extracted.getCardiacDiseaseFlag()
        );

        existing.setStrokeFlag(
                extracted.getStrokeFlag()
        );

        existing.setTuberculosisFlag(
                extracted.getTuberculosisFlag()
        );

        existing.setCarcinomaFlag(
                extracted.getCarcinomaFlag()
        );

        existing.setMalnourishmentFlag(
                extracted.getMalnourishmentFlag()
        );

        existing.setLowBirthWeightFlag(
                extracted.getLowBirthWeightFlag()
        );
    }


//    @Override
//    public ScreeningDataViewDTO getScreeningData(
//            String referenceId
//    ) {
//
//        ScreeningRecord screening =
//                validator.validate(
//                        referenceId
//                );
//
//        return ScreeningDataViewDTO
//                .builder()
//
//                .referenceId(
//                        referenceId
//                )
//
//                .category(
//                        screening.getScreeningCategory()
//                                .name()
//                )
//
//                .build();
//    }
    
    @Override
    public ScreeningDataViewDTO getScreeningData(
            String referenceId
    ) {

    	System.out.println("ref id "+ referenceId);
        ScreeningRecord screening =
                validator.validate(referenceId);
        

        ScreeningDataViewDTO.ScreeningDataViewDTOBuilder response =
                ScreeningDataViewDTO
                        .builder()

                        .referenceId(referenceId)

                        .category(
                                screening.getScreeningCategory()
                                        .name()
                        );

        switch (
                screening.getScreeningCategory()
        ) {

            case CAT1:

                ScreeningCat1 cat1 =
                        cat1Repo
                        .findByScreeningRecordId(
                                screening.getId()
                        )
                        .orElse(null);

                if (cat1 != null) {

                    response
                            .historySection(
                                    cat1.getHistorySection()
                            )

                            .generalExamSection(
                                    cat1.getGeneralExamSection()
                            )

                            .systemicSection(
                                    cat1.getSystemicSection()
                            )

                            .mandatorySection(
                                    cat1.getMandatorySection()
                            )

                            .diagnosisSection(
                                    cat1.getDiagnosisSection()
                            )

                            .prescriptionSection(
                                    cat1.getPrescriptionSection()
                            );
                }

                break;


            case CAT2:

                ScreeningCat2 cat2 =
                        cat2Repo
                        .findByScreeningRecordId(
                                screening.getId()
                        )
                        .orElse(null);

                if (cat2 != null) {

                    response
                            .historySection(
                                    cat2.getHistorySection()
                            )

                            .generalExamSection(
                                    cat2.getGeneralExamSection()
                            )

                            .systemicSection(
                                    cat2.getSystemicSection()
                            )

                            .mandatorySection(
                                    cat2.getMandatorySection()
                            )

                            .diagnosisSection(
                                    cat2.getDiagnosisSection()
                            )

                            .prescriptionSection(
                                    cat2.getPrescriptionSection()
                            );
                }

                break;


            case CAT3:

                ScreeningCat3 cat3 =
                        cat3Repo
                        .findByScreeningRecordId(
                                screening.getId()
                        )
                        .orElse(null);

                if (cat3 != null) {

                    response
                            .historySection(
                                    cat3.getHistorySection()
                            )

                            .generalExamSection(
                                    cat3.getGeneralExamSection()
                            )

                            .systemicSection(
                                    cat3.getSystemicSection()
                            )

                            .mandatorySection(
                                    cat3.getMandatorySection()
                            )

                            .diagnosisSection(
                                    cat3.getDiagnosisSection()
                            )

                            .prescriptionSection(
                                    cat3.getPrescriptionSection()
                            );
                }

                break;


            case CAT4:

                ScreeningCat4 cat4 =
                        cat4Repo
                        .findByScreeningRecordId(
                                screening.getId()
                        )
                        .orElse(null);

                if (cat4 != null) {

                    response
                            .historySection(
                                    cat4.getHistorySection()
                            )

                            .generalExamSection(
                                    cat4.getGeneralExamSection()
                            )

                            .systemicSection(
                                    cat4.getSystemicSection()
                            )

                            .mandatorySection(
                                    cat4.getMandatorySection()
                            )

                            .diagnosisSection(
                                    cat4.getDiagnosisSection()
                            )

                            .prescriptionSection(
                                    cat4.getPrescriptionSection()
                            );
                }

                break;


            case CAT5:

                ScreeningCat5 cat5 =
                        cat5Repo
                        .findByScreeningRecordId(
                                screening.getId()
                        )
                        .orElse(null);
                System.out.println("screening id: "+screening.getId());

                if (cat5 != null) {

                    response
                            .historySection(
                                    cat5.getHistorySection()
                            )

                            .generalExamSection(
                                    cat5.getGeneralExamSection()
                            )

                            .systemicSection(
                                    cat5.getSystemicSection()
                            )

                            .mandatorySection(
                                    cat5.getMandatorySection()
                            )

                            .diagnosisSection(
                                    cat5.getDiagnosisSection()
                            )

                            .prescriptionSection(
                                    cat5.getPrescriptionSection()
                            );
                }

                break;


            case CAT6:

                ScreeningCat6 cat6 =
                        cat6Repo
                        .findByScreeningRecordId(
                                screening.getId()
                        )
                        .orElse(null);

                if (cat6 != null) {

                    response
                            .historySection(
                                    cat6.getHistorySection()
                            )

                            .generalExamSection(
                                    cat6.getGeneralExamSection()
                            )

                            .systemicSection(
                                    cat6.getSystemicSection()
                            )

                            .mandatorySection(
                                    cat6.getMandatorySection()
                            )

                            .diagnosisSection(
                                    cat6.getDiagnosisSection()
                            )

                            .prescriptionSection(
                                    cat6.getPrescriptionSection()
                            );
                }

                break;
        }

        return response.build();
    }
}