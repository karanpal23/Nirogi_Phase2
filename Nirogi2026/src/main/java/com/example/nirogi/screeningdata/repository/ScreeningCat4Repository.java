package com.example.nirogi.screeningdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screeningdata.entity.ScreeningCat4;

public interface ScreeningCat4Repository
extends JpaRepository<ScreeningCat4, Long> {

Optional<ScreeningCat4>
findByScreeningRecordId(
    Long screeningRecordId
);
}
