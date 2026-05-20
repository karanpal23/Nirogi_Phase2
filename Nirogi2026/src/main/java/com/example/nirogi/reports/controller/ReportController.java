package com.example.nirogi.reports.controller;


import com.example.nirogi.reports.dto.ReportFilterDTO;
import com.example.nirogi.reports.service.ReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    /*
        BENEFICIARY CSV REPORT
     */
    @PostMapping("/beneficiary/csv")
    public ResponseEntity<ByteArrayResource>
    downloadBeneficiaryCsv(
            @RequestBody
            ReportFilterDTO filter
    ) {

        byte[] data =
                reportService
                        .generateBeneficiaryCsv(
                                filter
                        );

        return ResponseEntity
                .ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=beneficiary-report.csv"
                )

                .contentType(
                        MediaType.parseMediaType(
                                "text/csv"
                        )
                )

                .body(
                        new ByteArrayResource(data)
                );
    }


    /*
        BENEFICIARY EXCEL REPORT
     */
    @PostMapping("/beneficiary/excel")
    public ResponseEntity<ByteArrayResource>
    downloadBeneficiaryExcel(
            @RequestBody
            ReportFilterDTO filter
    ) {

        byte[] data =
                reportService
                        .generateBeneficiaryExcel(
                                filter
                        );

        return ResponseEntity
                .ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=beneficiary-report.xlsx"
                )

                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )

                .body(
                        new ByteArrayResource(data)
                );
    }


    /*
        CUMULATIVE CSV REPORT
     */
    @PostMapping("/cumulative/csv")
    public ResponseEntity<ByteArrayResource>
    downloadCumulativeCsv(
            @RequestBody
            ReportFilterDTO filter
    ) {

        byte[] data =
                reportService
                        .generateCumulativeCsv(
                                filter
                        );

        return ResponseEntity
                .ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=cumulative-report.csv"
                )

                .contentType(
                        MediaType.parseMediaType(
                                "text/csv"
                        )
                )

                .body(
                        new ByteArrayResource(data)
                );
    }


    /*
        CUMULATIVE EXCEL REPORT
     */
    @PostMapping("/cumulative/excel")
    public ResponseEntity<ByteArrayResource>
    downloadCumulativeExcel(
            @RequestBody
            ReportFilterDTO filter
    ) {

        byte[] data =
                reportService
                        .generateCumulativeExcel(
                                filter
                        );

        return ResponseEntity
                .ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=cumulative-report.xlsx"
                )

                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )

                .body(
                        new ByteArrayResource(data)
                );
    }
}