package com.example.nirogi.screening.entity;


import com.example.nirogi.screening.enums.LabOrderStatus;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "screening_lab_order",
        indexes = {

                @Index(
                        name = "idx_order_reference",
                        columnList = "referenceId"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningLabOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long screeningRecordId;

    private String referenceId;

    @Enumerated(EnumType.STRING)
    private LabOrderStatus status;

    private Long orderedBy;

    private LocalDateTime orderedAt;

    private LocalDateTime completedAt;
}
