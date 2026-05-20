package com.example.nirogi.screening.entity;


import com.example.nirogi.screening.enums.LabTestStatus;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "screening_lab_test",
        indexes = {

                @Index(
                        name = "idx_test_order",
                        columnList = "labOrderId"
                ),

                @Index(
                        name = "idx_test_code",
                        columnList = "testCode"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningLabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long labOrderId;

    private String testCode;

    private String testName;

    @Enumerated(EnumType.STRING)
    private LabTestStatus status;

    private String resultValue;

    private String remarks;

    private String normalRange;

    private Long completedBy;

    private LocalDateTime completedAt;
}
