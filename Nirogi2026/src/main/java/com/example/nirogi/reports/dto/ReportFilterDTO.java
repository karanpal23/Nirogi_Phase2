package com.example.nirogi.reports.dto;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportFilterDTO {

    private Long districtId;

    private Long facilityId;

    private String category;

    private LocalDate fromDate;

    private LocalDate toDate;
}