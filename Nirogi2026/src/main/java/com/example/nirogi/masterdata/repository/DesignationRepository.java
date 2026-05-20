package com.example.nirogi.masterdata.repository;


import com.example.nirogi.masterdata.entity.Designation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DesignationRepository
        extends JpaRepository<Designation, Long> {

	 	boolean existsByNameIgnoreCase(String name);

	    long count();

//	    boolean existsByDistrictCode(String code);
}