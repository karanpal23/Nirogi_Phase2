package com.example.nirogi.beneficiary.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.nirogi.beneficiary.enums.OperationType;
import com.example.nirogi.beneficiary.enums.SyncStatus;

import lombok.*;

@Entity
@Table(
        name = "beneficiary_sync_log",
        indexes = {

                @Index(
                        name = "idx_sync_member",
                        columnList = "memberId"
                ),

                @Index(
                        name = "idx_sync_batch",
                        columnList = "batchId"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiarySyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;

    private String batchId;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Enumerated(EnumType.STRING)
    private SyncStatus status;

    private String message;

    private LocalDateTime processedAt;
}