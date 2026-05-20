package com.example.nirogi.screeningdata.repository;


import com.example.nirogi.screeningdata.entity.ScreeningCat1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreeningCat1Repository
        extends JpaRepository<ScreeningCat1, Long> {

    Optional<ScreeningCat1>
    findByScreeningRecordId(
            Long screeningRecordId
    );
}
