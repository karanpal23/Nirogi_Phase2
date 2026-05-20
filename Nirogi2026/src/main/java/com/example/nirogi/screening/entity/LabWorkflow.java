package com.example.nirogi.screening.entity;


import com.example.nirogi.screening.enums.LabStatus;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "lab_workflow",
        indexes = {

                @Index(
                        name = "idx_lab_screening",
                        columnList = "screeningRecordId"
                ),

                @Index(
                        name = "idx_lab_status",
                        columnList = "labStatus"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabWorkflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long screeningRecordId;

    @Enumerated(EnumType.STRING)
    private LabStatus labStatus;

    private LocalDateTime sentAt;

    private LocalDateTime dueDate;

    private LocalDateTime receivedAt;

    private Long enteredBy;
}
