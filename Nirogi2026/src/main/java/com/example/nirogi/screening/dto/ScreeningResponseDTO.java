package com.example.nirogi.screening.dto;

import lombok.*;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningResponseDTO {

    private String referenceId;

    private String status;

    private String eligibleScheme;

    private String message;
}