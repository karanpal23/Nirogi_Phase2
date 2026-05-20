package com.example.nirogi.screening.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResultItemDTO {

    private String testCode;

    private String resultValue;

    private String remarks;

    private String normalRange;
}
