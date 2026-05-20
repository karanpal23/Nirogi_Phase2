package com.example.nirogi.masterdata.repository;



import com.example.nirogi.masterdata.entity.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacilityTypeRepository
        extends JpaRepository<FacilityType, Long> {

	 	boolean existsByNameIgnoreCase(String name);

	    long count();

//	    boolean existsByDistrictCode(String code);
}
