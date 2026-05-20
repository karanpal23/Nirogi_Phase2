package com.example.nirogi.beneficiary.repository;

import com.example.nirogi.beneficiary.entity.Beneficiary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeneficiarySearchRepository
        extends JpaRepository<Beneficiary, Long> {

    /*
        SEARCH BY PPP ID
     */

    @Query(
            "SELECT b " +
            "FROM Beneficiary b " +
            "WHERE " +
            "(:districtId IS NULL " +
            "OR b.district = :districtId) " +
            "AND " +
            "b.pppId = :pppId"
    )
    List<Beneficiary> searchByPppId(

            @Param("districtId")
            String districtId,

            @Param("pppId")
            String pppId
    );


    /*
        SEARCH BY NAME + MOBILE
     */

    @Query(
            "SELECT b " +
            "FROM Beneficiary b " +
            "WHERE " +
            "(:districtId IS NULL " +
            "OR b.district = :districtId) " +
            "AND " +
            "LOWER(b.firstName) " +
            "LIKE LOWER(CONCAT('%', :firstName, '%')) " +
            "AND " +
            "b.mobileNo = :mobileNo"
    )
    List<Beneficiary> searchByNameAndMobile(

            @Param("districtId")
            String districtId,

            @Param("firstName")
            String firstName,

            @Param("mobileNo")
            String mobileNo
    );
}