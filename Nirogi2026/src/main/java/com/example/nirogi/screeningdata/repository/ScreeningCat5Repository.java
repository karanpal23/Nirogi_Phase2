package com.example.nirogi.screeningdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screeningdata.entity.ScreeningCat5;

public interface ScreeningCat5Repository
extends JpaRepository<ScreeningCat5, Long> {

Optional<ScreeningCat5>
findByScreeningRecordId(
    Long screeningRecordId
);
}