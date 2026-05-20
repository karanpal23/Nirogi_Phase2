package com.example.nirogi.reports.serviceImpl;


import com.example.nirogi.reports.dto.*;
import com.example.nirogi.reports.exporter.*;
import com.example.nirogi.reports.repository.ReportQueryRepository;
import com.example.nirogi.reports.service.ReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl
        implements ReportService {

    private final ReportQueryRepository repository;

    private final CsvReportExporter csvExporter;

    private final ExcelReportExporter excelExporter;


    @Override
    public byte[] generateBeneficiaryCsv(
            ReportFilterDTO filter
    ) {

        List<BeneficiaryReportRowDTO> rows =
                repository
                        .getBeneficiaryReport(
                                filter
                        );

        return csvExporter
                .exportBeneficiaryReport(
                        rows
                );
    }


    @Override
    public byte[] generateBeneficiaryExcel(
            ReportFilterDTO filter
    ) {

        List<BeneficiaryReportRowDTO> rows =
                repository
                        .getBeneficiaryReport(
                                filter
                        );

        return excelExporter
                .exportBeneficiaryReport(
                        rows
                );
    }


    @Override
    public byte[] generateCumulativeCsv(
            ReportFilterDTO filter
    ) {

        List<CumulativeReportRowDTO> rows =
                repository
                        .getCumulativeReport(
                                filter
                        );

        return csvExporter
                .exportCumulativeReport(
                        rows
                );
    }


    @Override
    public byte[] generateCumulativeExcel(
            ReportFilterDTO filter
    ) {

        List<CumulativeReportRowDTO> rows =
                repository
                        .getCumulativeReport(
                                filter
                        );

        return excelExporter
                .exportCumulativeReport(
                        rows
                );
    }
}