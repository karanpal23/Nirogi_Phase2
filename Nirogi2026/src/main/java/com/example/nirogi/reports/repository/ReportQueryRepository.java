package com.example.nirogi.reports.repository;

import com.example.nirogi.reports.dto.BeneficiaryReportRowDTO;
import com.example.nirogi.reports.dto.CumulativeReportRowDTO;
import com.example.nirogi.reports.dto.ReportFilterDTO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportQueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BeneficiaryReportRowDTO> getBeneficiaryReport(
            ReportFilterDTO filter
    ) {

        String sql =
                "SELECT " +
                "ROW_NUMBER() OVER(ORDER BY sr.id) AS serialNo, " +
                "d.name AS district, " +
                "f.name AS facilityName, " +
                "'Doctor' AS designation, " +
                "sr.reference_id AS referenceId, " +
                "b.ppp_id AS pppId, " +
                "CONCAT(b.first_name, ' ', b.last_name) AS beneficiaryName, " +
                "CONCAT(b.age, ' yr ', b.gender) AS ageGender, " +
                "b.address AS address, " +
                "COALESCE(sci.other_diagnosis, 'No') AS diagnosisAlreadyKnown, " +
                "COALESCE(sci.diagnosis_code, 'undefined') AS diagnosed, " +
                "CASE " +
                "WHEN lw.sent_at IS NOT NULL THEN 'Yes' " +
                "ELSE 'No' " +
                "END AS sampleSent, " +
                "CASE " +
                "WHEN lw.received_at IS NOT NULL THEN 'Yes' " +
                "ELSE 'No' " +
                "END AS reportEntered, " +
                "sr.screening_category AS category " +

                "FROM screening_record sr " +

                "JOIN beneficiary b " +
                "ON sr.beneficiary_id = b.id " +

                "LEFT JOIN screening_clinical_indicator sci " +
                "ON sci.screening_record_id = sr.id " +

                "LEFT JOIN lab_workflow lw " +
                "ON lw.screening_record_id = sr.id " +

                "LEFT JOIN facility f " +
                "ON sr.facility_id = f.id " +

                "LEFT JOIN district d " +
                "ON sr.district_id = d.id " +

                "WHERE sr.created_at BETWEEN ? AND ?";

        return jdbcTemplate.query(

                sql,

                new Object[]{
                        filter.getFromDate(),
                        filter.getToDate()
                },

                (rs, rowNum) ->

                        BeneficiaryReportRowDTO
                                .builder()

                                .serialNo(
                                        rs.getLong("serialNo")
                                )

                                .district(
                                        rs.getString("district")
                                )

                                .facilityName(
                                        rs.getString("facilityName")
                                )

                                .designation(
                                        rs.getString("designation")
                                )

                                .referenceId(
                                        rs.getString("referenceId")
                                )

                                .pppId(
                                        rs.getString("pppId")
                                )

                                .beneficiaryName(
                                        rs.getString("beneficiaryName")
                                )

                                .ageGender(
                                        rs.getString("ageGender")
                                )

                                .address(
                                        rs.getString("address")
                                )

                                .diagnosisAlreadyKnown(
                                        rs.getString("diagnosisAlreadyKnown")
                                )

                                .diagnosed(
                                        rs.getString("diagnosed")
                                )

                                .sampleSent(
                                        rs.getString("sampleSent")
                                )

                                .reportEntered(
                                        rs.getString("reportEntered")
                                )

                                .category(
                                        rs.getString("category")
                                )

                                .build()
        );
    }

    
    public List<CumulativeReportRowDTO> getCumulativeReport(
            ReportFilterDTO filter
    ) {

        String sql =
                "SELECT " +

                "ROW_NUMBER() OVER(ORDER BY d.name) AS serialNo, " +

                "d.name AS district, " +
                "f.name AS facilityName, " +
                "ft.name AS facilityType, " +

                /* CATEGORY COUNTS */

                "SUM(CASE WHEN sr.screening_category = 'CAT1' THEN 1 ELSE 0 END) AS cat1, " +

                "SUM(CASE WHEN sr.screening_category = 'CAT2' THEN 1 ELSE 0 END) AS cat2, " +

                "SUM(CASE WHEN sr.screening_category = 'CAT3' THEN 1 ELSE 0 END) AS cat3, " +

                "SUM(CASE WHEN sr.screening_category = 'CAT4' THEN 1 ELSE 0 END) AS cat4, " +

                "SUM(CASE WHEN sr.screening_category = 'CAT5' THEN 1 ELSE 0 END) AS cat5, " +

                "SUM(CASE WHEN sr.screening_category = 'CAT6' THEN 1 ELSE 0 END) AS cat6, " +

                "COUNT(sr.id) AS totalPatients, " +

                /* LAB TESTS */

                "SUM(CASE WHEN slt.test_code = 'HB' THEN 1 ELSE 0 END) AS hb, " +

                "SUM(CASE WHEN slt.test_code = 'CBC' THEN 1 ELSE 0 END) AS cbc, " +

                "SUM(CASE WHEN slt.test_code IN ('FBS', 'RBS') THEN 1 ELSE 0 END) AS fbsRbs, " +

                "SUM(CASE WHEN slt.test_code = 'SERUM_CHOLESTEROL' THEN 1 ELSE 0 END) AS serumCholesterol, " +

                "SUM(CASE WHEN slt.test_code = 'BLOOD_UREA' THEN 1 ELSE 0 END) AS bloodUrea, " +

                "SUM(CASE WHEN slt.test_code = 'SERUM_CREATININE' THEN 1 ELSE 0 END) AS serumCreatinine, " +

                "SUM(CASE WHEN slt.test_code = 'TSH' THEN 1 ELSE 0 END) AS tsh, " +

                "SUM(CASE WHEN slt.test_code = 'PSA' THEN 1 ELSE 0 END) AS psa, " +

                "SUM(CASE WHEN slt.test_code = 'PAP_SMEAR' THEN 1 ELSE 0 END) AS papSmear, " +

                "SUM(CASE WHEN slt.test_code = 'URINE_RE' THEN 1 ELSE 0 END) AS urineRe, " +

                "COUNT(slt.id) AS totalLabInvestigations, " +

                /* DISEASE DETECTED */

                "SUM(CASE WHEN sci.anemia_flag = true THEN 1 ELSE 0 END) AS anaemia, " +

                "SUM(CASE WHEN sci.malnourishment_flag = true THEN 1 ELSE 0 END) AS malnourishedChildren, " +

                "SUM(CASE WHEN sci.low_birth_weight_flag = true THEN 1 ELSE 0 END) AS lbw, " +

                "SUM(CASE WHEN sci.tuberculosis_flag = true THEN 1 ELSE 0 END) AS tuberculosis, " +

                "SUM(CASE WHEN sci.carcinoma_flag = true THEN 1 ELSE 0 END) AS carcinoma, " +

                "SUM(CASE WHEN sci.diabetes_flag = true THEN 1 ELSE 0 END) AS diabetesMellitus, " +

                "SUM(CASE WHEN sci.hypertension_flag = true THEN 1 ELSE 0 END) AS hypertension, " +

                "SUM(CASE WHEN sci.cardiac_disease_flag = true THEN 1 ELSE 0 END) AS cardiacDisease, " +

                "SUM(CASE WHEN sci.stroke_flag = true THEN 1 ELSE 0 END) AS stroke, " +

                "SUM(CASE WHEN sci.other_diagnosis IS NOT NULL THEN 1 ELSE 0 END) AS others, " +

                "COUNT(sci.id) AS totalDetected, " +

                "0 AS referred " +

                "FROM screening_record sr " +

                "LEFT JOIN screening_clinical_indicator sci " +
                "ON sci.screening_record_id = sr.id " +

                "LEFT JOIN screening_lab_order slo " +
                "ON slo.screening_record_id = sr.id " +

                "LEFT JOIN screening_lab_test slt " +
                "ON slt.lab_order_id = slo.id " +

                "LEFT JOIN facility f " +
                "ON sr.facility_id = f.id " +

                "LEFT JOIN facility_type ft " +
                "ON f.facility_type_id = ft.id " +

                "LEFT JOIN district d " +
                "ON sr.district_id = d.id " +

                "WHERE sr.created_at BETWEEN ? AND ? " +

                "GROUP BY d.name, f.name, ft.name";

        return jdbcTemplate.query(

                sql,

                new Object[]{
                        filter.getFromDate(),
                        filter.getToDate()
                },

                (rs, rowNum) ->

                        CumulativeReportRowDTO
                                .builder()

                                .serialNo(
                                        rs.getLong("serialNo")
                                )

                                .district(
                                        rs.getString("district")
                                )

                                .facilityName(
                                        rs.getString("facilityName")
                                )

                                .facilityType(
                                        rs.getString("facilityType")
                                )

                                .cat1(
                                        rs.getLong("cat1")
                                )

                                .cat2(
                                        rs.getLong("cat2")
                                )

                                .cat3(
                                        rs.getLong("cat3")
                                )

                                .cat4(
                                        rs.getLong("cat4")
                                )

                                .cat5(
                                        rs.getLong("cat5")
                                )

                                .cat6(
                                        rs.getLong("cat6")
                                )

                                .totalPatients(
                                        rs.getLong("totalPatients")
                                )

                                .hb(
                                        rs.getLong("hb")
                                )

                                .cbc(
                                        rs.getLong("cbc")
                                )

                                .fbsRbs(
                                        rs.getLong("fbsRbs")
                                )

                                .serumCholesterol(
                                        rs.getLong("serumCholesterol")
                                )

                                .bloodUrea(
                                        rs.getLong("bloodUrea")
                                )

                                .serumCreatinine(
                                        rs.getLong("serumCreatinine")
                                )

                                .tsh(
                                        rs.getLong("tsh")
                                )

                                .psa(
                                        rs.getLong("psa")
                                )

                                .papSmear(
                                        rs.getLong("papSmear")
                                )

                                .urineRe(
                                        rs.getLong("urineRe")
                                )

                                .totalLabInvestigations(
                                        rs.getLong("totalLabInvestigations")
                                )

                                .anaemia(
                                        rs.getLong("anaemia")
                                )

                                .malnourishedChildren(
                                        rs.getLong("malnourishedChildren")
                                )

                                .lbw(
                                        rs.getLong("lbw")
                                )

                                .tuberculosis(
                                        rs.getLong("tuberculosis")
                                )

                                .carcinoma(
                                        rs.getLong("carcinoma")
                                )

                                .diabetesMellitus(
                                        rs.getLong("diabetesMellitus")
                                )

                                .hypertension(
                                        rs.getLong("hypertension")
                                )

                                .cardiacDisease(
                                        rs.getLong("cardiacDisease")
                                )

                                .stroke(
                                        rs.getLong("stroke")
                                )

                                .others(
                                        rs.getLong("others")
                                )

                                .totalDetected(
                                        rs.getLong("totalDetected")
                                )

                                .referred(
                                        rs.getLong("referred")
                                )

                                .build()
        );
    }
}