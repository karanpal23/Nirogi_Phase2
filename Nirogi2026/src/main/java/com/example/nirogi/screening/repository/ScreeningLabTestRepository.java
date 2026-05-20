package com.example.nirogi.screening.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screening.entity.ScreeningLabTest;

public interface ScreeningLabTestRepository
extends JpaRepository<
        ScreeningLabTest,
        Long
> {

List<ScreeningLabTest>
findByLabOrderId(
    Long labOrderId
);

Optional<ScreeningLabTest>
findByLabOrderIdAndTestCode(
    Long labOrderId,
    String testCode
);
}
