package com.example.nirogi.masterdata.dto;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultDTO {

    private int totalRecords;

    private int successCount;

    private int failedCount;

    private List<String> errors;
}
