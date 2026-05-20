package com.example.nirogi.screeningdata.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "screening_cat6",
        indexes = {

                @Index(
                        name = "idx_cat6_screening",
                        columnList = "screeningRecordId"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningCat6 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long screeningRecordId;

    /*
        JSON VALUES
     */

    @Column(columnDefinition = "LONGTEXT")
    private String historySection;

    @Column(columnDefinition = "LONGTEXT")
    private String generalExamSection;

    @Column(columnDefinition = "LONGTEXT")
    private String systemicSection;

    @Column(columnDefinition = "LONGTEXT")
    private String mandatorySection;

    @Column(columnDefinition = "LONGTEXT")
    private String diagnosisSection;

    @Column(columnDefinition = "LONGTEXT")
    private String prescriptionSection;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}