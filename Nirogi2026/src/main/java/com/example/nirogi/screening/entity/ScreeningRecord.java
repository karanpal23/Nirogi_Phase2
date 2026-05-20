package com.example.nirogi.screening.entity;


import com.example.nirogi.screening.enums.*;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "screening_record",
        indexes = {

                @Index(
                        name = "idx_screening_beneficiary",
                        columnList = "beneficiaryId"
                ),

                @Index(
                        name = "idx_screening_reference",
                        columnList = "referenceId"
                ),

                @Index(
                        name = "idx_screening_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_screening_date",
                        columnList = "screeningDate"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String referenceId;

    private Long beneficiaryId;

    @Enumerated(EnumType.STRING)
    private ScreeningCategory screeningCategory;

    private Long districtId;

    private Long facilityId;

    private Long doctorId;
    private String eligibleScheme;

    private Boolean labRequired;

    private LocalDateTime screeningDate;

    private LocalDateTime labDueDate;

    @Enumerated(EnumType.STRING)
    private ScreeningStatus status;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;
}