package com.example.nirogi.reports.exporter;


import com.example.nirogi.reports.dto.*;
import com.opencsv.CSVWriter;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import java.util.List;

@Component
public class CsvReportExporter {

    public byte[] exportBeneficiaryReport(
            List<BeneficiaryReportRowDTO> rows
    ) {

        try {

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            CSVWriter writer =
                    new CSVWriter(
                            new OutputStreamWriter(out)
                    );

            writer.writeNext(
                    new String[] {

                            "S.No",
                            "district",
                            "facility_Name",
                            "designation",
                            "reference_ID",
                            "ppp_ID",
                            "name_of_Beneficiary",
                            "age_Gender",
                            "address",
                            "diagnosis_Already_Known",
                            "diagnosed",
                            "sample_Sent",
                            "report_Entered",
                            "category"
                    }
            );

            for (BeneficiaryReportRowDTO row
                    : rows) {

            	writer.writeNext(
            	        new String[] {

            	                String.valueOf(
            	                        row.getSerialNo()
            	                ),

            	                row.getDistrict(),

            	                row.getFacilityName(),

            	                row.getDesignation(),

            	                row.getReferenceId(),

            	                row.getPppId(),

            	                row.getBeneficiaryName(),

            	                row.getAgeGender(),

            	                row.getAddress(),

            	                row.getDiagnosisAlreadyKnown(),

            	                row.getDiagnosed(),

            	                row.getSampleSent(),

            	                row.getReportEntered(),

            	                row.getCategory()
            	        }
            	);
            }

            writer.close();

            return out.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to export CSV"
            );
        }
    }


    public byte[] exportCumulativeReport(
        List<CumulativeReportRowDTO> rows
) {

    try {

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        CSVWriter writer =
                new CSVWriter(
                        new OutputStreamWriter(out)
                );

        /*
            HEADER
         */

        writer.writeNext(
                new String[] {

                        "Sr.No",

                        "District",

                        "Facility Name",

                        "Facility Type",

                        /*
                            PATIENT VISITS
                         */

                        "CAT I",
                        "CAT II",
                        "CAT III",
                        "CAT IV",
                        "CAT V",
                        "CAT VI",
                        "Total Patients",

                        /*
                            LAB INVESTIGATIONS
                         */

                        "Hb",
                        "CBC",
                        "FBS/RBS",
                        "Serum Cholesterol",
                        "Blood Urea",
                        "Serum Creatinine",
                        "TSH",
                        "PSA",
                        "PAP Smear",
                        "Urine R/E",
                        "Total Lab Investigations",

                        /*
                            DISEASE DETECTED
                         */

                        "Anaemia",
                        "Malnourished Children",
                        "LBW",
                        "Tuberculosis",
                        "Carcinoma",
                        "Diabetes Mellitus",
                        "Hypertension",
                        "Cardiac Disease",
                        "Stroke",
                        "Others",
                        "Total Detected",
                        "Referred"
                }
        );

        /*
            DATA ROWS
         */

        for (CumulativeReportRowDTO row
                : rows) {

            writer.writeNext(
                    new String[] {

                            /*
                                BASIC INFO
                             */

                            String.valueOf(
                                    row.getSerialNo()
                            ),

                            row.getDistrict(),

                            row.getFacilityName(),

                            row.getFacilityType(),

                            /*
                                CATEGORY COUNTS
                             */

                            String.valueOf(
                                    row.getCat1()
                            ),

                            String.valueOf(
                                    row.getCat2()
                            ),

                            String.valueOf(
                                    row.getCat3()
                            ),

                            String.valueOf(
                                    row.getCat4()
                            ),

                            String.valueOf(
                                    row.getCat5()
                            ),

                            String.valueOf(
                                    row.getCat6()
                            ),

                            String.valueOf(
                                    row.getTotalPatients()
                            ),

                            /*
                                LAB INVESTIGATIONS
                             */

                            String.valueOf(
                                    row.getHb()
                            ),

                            String.valueOf(
                                    row.getCbc()
                            ),

                            String.valueOf(
                                    row.getFbsRbs()
                            ),

                            String.valueOf(
                                    row.getSerumCholesterol()
                            ),

                            String.valueOf(
                                    row.getBloodUrea()
                            ),

                            String.valueOf(
                                    row.getSerumCreatinine()
                            ),

                            String.valueOf(
                                    row.getTsh()
                            ),

                            String.valueOf(
                                    row.getPsa()
                            ),

                            String.valueOf(
                                    row.getPapSmear()
                            ),

                            String.valueOf(
                                    row.getUrineRe()
                            ),

                            String.valueOf(
                                    row.getTotalLabInvestigations()
                            ),

                            /*
                                DISEASE DETECTED
                             */

                            String.valueOf(
                                    row.getAnaemia()
                            ),

                            String.valueOf(
                                    row.getMalnourishedChildren()
                            ),

                            String.valueOf(
                                    row.getLbw()
                            ),

                            String.valueOf(
                                    row.getTuberculosis()
                            ),

                            String.valueOf(
                                    row.getCarcinoma()
                            ),

                            String.valueOf(
                                    row.getDiabetesMellitus()
                            ),

                            String.valueOf(
                                    row.getHypertension()
                            ),

                            String.valueOf(
                                    row.getCardiacDisease()
                            ),

                            String.valueOf(
                                    row.getStroke()
                            ),

                            String.valueOf(
                                    row.getOthers()
                            ),

                            String.valueOf(
                                    row.getTotalDetected()
                            ),

                            String.valueOf(
                                    row.getReferred()
                            )
                    }
            );
        }

        writer.close();

        return out.toByteArray();

    } catch (Exception ex) {

        throw new RuntimeException(
                "Failed to export cumulative CSV report"
        );
    }
}
}