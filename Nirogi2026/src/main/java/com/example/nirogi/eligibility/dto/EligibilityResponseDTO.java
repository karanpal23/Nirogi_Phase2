package com.example.nirogi.eligibility.dto;



import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityResponseDTO {

    private String memberId;

    private Boolean eligible;

    private String eligibleScheme;

    private Double income;

    private Integer age;

    private String reason;
}