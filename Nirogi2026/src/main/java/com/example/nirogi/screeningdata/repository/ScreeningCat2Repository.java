package com.example.nirogi.screeningdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screeningdata.entity.ScreeningCat2;

public interface ScreeningCat2Repository
extends JpaRepository<ScreeningCat2, Long> {

Optional<ScreeningCat2>
findByScreeningRecordId(
    Long screeningRecordId
);
}
