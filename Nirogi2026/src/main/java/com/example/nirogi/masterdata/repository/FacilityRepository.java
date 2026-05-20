package com.example.nirogi.masterdata.repository;

import com.example.nirogi.masterdata.entity.Facility;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    boolean existsByFacilityCode(String code);

    long count();

    List<Facility> findByDistrictIdAndIsActiveTrue(Long districtId);
}
