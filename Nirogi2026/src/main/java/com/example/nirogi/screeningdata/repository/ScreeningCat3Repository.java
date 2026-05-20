package com.example.nirogi.screeningdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screeningdata.entity.ScreeningCat3;

public interface ScreeningCat3Repository
extends JpaRepository<ScreeningCat3, Long> {

Optional<ScreeningCat3>
findByScreeningRecordId(
    Long screeningRecordId
);
}
