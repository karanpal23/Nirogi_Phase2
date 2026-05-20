package com.example.nirogi.eligibility.entity;


import com.example.nirogi.eligibility.enums.*;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "eligibility_scheme",
        indexes = {

                @Index(
                        name = "idx_scheme_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_scheme_code",
                        columnList = "schemeCode"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EligibilityScheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SchemeCode schemeCode;

    private String schemeName;

    /*
        for age based schemes
     */
    private Integer minAge;

    private Integer maxAge;

    /*
        for income based schemes
     */
    private Double incomeLimit;

    private Boolean incomeBased;

    @Enumerated(EnumType.STRING)
    private SchemeStatus status;

    private Long createdBy;

    private LocalDateTime createdAt;
}