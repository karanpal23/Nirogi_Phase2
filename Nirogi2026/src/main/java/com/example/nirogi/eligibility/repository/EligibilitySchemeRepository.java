package com.example.nirogi.eligibility.repository;



import com.example.nirogi.eligibility.entity.EligibilityScheme;
import com.example.nirogi.eligibility.enums.SchemeStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EligibilitySchemeRepository
        extends JpaRepository<EligibilityScheme, Long> {

    List<EligibilityScheme>
    findByStatus(SchemeStatus status);
}
