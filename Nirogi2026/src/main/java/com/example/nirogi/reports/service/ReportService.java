package com.example.nirogi.reports.service;


import com.example.nirogi.reports.dto.ReportFilterDTO;

public interface ReportService {

    byte[] generateBeneficiaryCsv(
            ReportFilterDTO filter
    );

    byte[] generateBeneficiaryExcel(
            ReportFilterDTO filter
    );

    byte[] generateCumulativeCsv(
            ReportFilterDTO filter
    );

    byte[] generateCumulativeExcel(
            ReportFilterDTO filter
    );
}