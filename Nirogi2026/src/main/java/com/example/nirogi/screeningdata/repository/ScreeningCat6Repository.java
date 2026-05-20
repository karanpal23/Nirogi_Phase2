package com.example.nirogi.screeningdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nirogi.screeningdata.entity.ScreeningCat6;

public interface ScreeningCat6Repository
extends JpaRepository<ScreeningCat6, Long> {

Optional<ScreeningCat6>
findByScreeningRecordId(
    Long screeningRecordId
);
}
