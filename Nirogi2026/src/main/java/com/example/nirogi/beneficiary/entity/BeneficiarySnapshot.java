package com.example.nirogi.beneficiary.entity;


import com.example.nirogi.beneficiary.enums.BeneficiaryStatus;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "beneficiary_snapshot",
        indexes = {

                @Index(
                        name = "idx_snapshot_beneficiary",
                        columnList = "beneficiaryId"
                ),

                @Index(
                        name = "idx_snapshot_latest",
                        columnList = "isLatest"
                ),

                @Index(
                        name = "idx_snapshot_hash",
                        columnList = "recordHash"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiarySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long beneficiaryId;

    private String pppId;

    private Double income;

    @Column(length = 200)
    private String recordHash;

    private Integer versionNo;

    private Boolean isLatest;

    @Enumerated(EnumType.STRING)
    private BeneficiaryStatus beneficiaryStatus;

    private String batchId;

    private LocalDateTime createdAt;
}