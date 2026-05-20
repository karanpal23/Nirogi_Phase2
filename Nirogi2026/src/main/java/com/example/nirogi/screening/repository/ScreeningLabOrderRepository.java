package com.example.nirogi.screening.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screening.entity.ScreeningLabOrder;

public interface ScreeningLabOrderRepository
extends JpaRepository<
        ScreeningLabOrder,
        Long
> {

Optional<ScreeningLabOrder>
findByReferenceId(
    String referenceId
);
}
