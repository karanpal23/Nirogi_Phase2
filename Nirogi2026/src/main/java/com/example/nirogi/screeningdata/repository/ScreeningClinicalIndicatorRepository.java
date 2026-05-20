package com.example.nirogi.screeningdata.repository;


import com.example.nirogi.screeningdata.entity.ScreeningClinicalIndicator;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningClinicalIndicatorRepository
        extends JpaRepository<ScreeningClinicalIndicator, Long> {
	
	Optional<ScreeningClinicalIndicator>
	findByScreeningRecordId(
	        Long screeningRecordId
	);
}
