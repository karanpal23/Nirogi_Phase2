package com.example.nirogi.masterdata.repository;


import com.example.nirogi.masterdata.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByDistrictCode(String code);

    long count();
}