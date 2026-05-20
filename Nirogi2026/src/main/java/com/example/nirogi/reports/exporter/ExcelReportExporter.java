package com.example.nirogi.reports.exporter;


import com.example.nirogi.reports.dto.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

import java.util.List;

@Component
public class ExcelReportExporter {

    public byte[] exportBeneficiaryReport(
            List<BeneficiaryReportRowDTO> rows
    ) {

        try {

            Workbook workbook =
                    new XSSFWorkbook();

            Sheet sheet =
                    workbook.createSheet(
                            "Beneficiary Report"
                    );

            Row header =
                    sheet.createRow(0);

            header.createCell(0)
                    .setCellValue("District");

            header.createCell(1)
                    .setCellValue("Facility");

            header.createCell(2)
                    .setCellValue("Reference ID");

            int rowNum = 1;

            for (BeneficiaryReportRowDTO row
                    : rows) {

                Row excelRow =
                        sheet.createRow(rowNum++);

                excelRow.createCell(0)
                        .setCellValue(
                                row.getDistrict()
                        );

                excelRow.createCell(1)
                        .setCellValue(
                                row.getFacilityName()
                        );

                excelRow.createCell(2)
                        .setCellValue(
                                row.getReferenceId()
                        );
            }

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            return out.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to export Excel"
            );
        }
    }


    public byte[] exportCumulativeReport(
            List<CumulativeReportRowDTO> rows
    ) {

        try {

            Workbook workbook =
                    new XSSFWorkbook();

            Sheet sheet =
                    workbook.createSheet(
                            "Cumulative Report"
                    );

            Row header =
                    sheet.createRow(0);

            header.createCell(0)
                    .setCellValue("District");

            header.createCell(1)
                    .setCellValue("Facility");

            header.createCell(2)
                    .setCellValue("CAT1");

            int rowNum = 1;

            for (CumulativeReportRowDTO row
                    : rows) {

                Row excelRow =
                        sheet.createRow(rowNum++);

                excelRow.createCell(0)
                        .setCellValue(
                                row.getDistrict()
                        );

                excelRow.createCell(1)
                        .setCellValue(
                                row.getFacilityName()
                        );

                excelRow.createCell(2)
                        .setCellValue(
                                row.getCat1()
                        );
            }

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            return out.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to export Excel"
            );
        }
    }
}