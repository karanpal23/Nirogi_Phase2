package com.example.nirogi.screening.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLabOrderDTO {

    private String referenceId;

    private List<String> tests;
}
