package com.example.nirogi.screeningdata.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningDataResponseDTO {

    private String referenceId;

    private String message;
}
