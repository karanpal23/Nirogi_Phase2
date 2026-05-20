package com.example.nirogi.beneficiary.entity;

import com.example.nirogi.beneficiary.enums.BatchStatus;
import com.example.nirogi.beneficiary.enums.OperationType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "upload_batch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String batchId;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Long uploadedBy;

    private LocalDateTime uploadedAt;

    private int totalRecords;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;
}