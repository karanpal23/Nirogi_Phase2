package com.example.nirogi.masterdata.service;


import com.example.nirogi.masterdata.dto.MasterDataResponseDTO;
import com.example.nirogi.masterdata.dto.CreateDesignationRequestDTO;
import com.example.nirogi.masterdata.dto.CreateDistrictRequestDTO;
import com.example.nirogi.masterdata.dto.CreateFacilityRequestDTO;
import com.example.nirogi.masterdata.dto.CreateFacilityTypeRequestDTO;
import com.example.nirogi.masterdata.dto.CreateMasterRequestDTO;
import com.example.nirogi.masterdata.dto.ImportResultDTO;
import com.example.nirogi.masterdata.dto.MasterDataItemDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;




public interface MasterDataService {

    // CREATE
    MasterDataItemDTO createDistrict(CreateDistrictRequestDTO request, Long adminId);

    MasterDataItemDTO createFacilityType(CreateFacilityTypeRequestDTO request, Long adminId);

    MasterDataItemDTO createDesignation(CreateDesignationRequestDTO request, Long adminId);

    MasterDataItemDTO createFacility(CreateFacilityRequestDTO request, Long adminId);

    // IMPORT
    ImportResultDTO importDistricts(MultipartFile file, Long adminId) throws Exception;

    // FETCH
    MasterDataResponseDTO getAllMasterData();

    List<MasterDataItemDTO> getFacilitiesByDistrict(Long districtId);
}