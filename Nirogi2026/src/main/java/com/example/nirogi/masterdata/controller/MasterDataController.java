package com.example.nirogi.masterdata.controller;



import com.example.nirogi.masterdata.dto.*;
import com.example.nirogi.masterdata.service.MasterDataService;
import com.example.nirogi.auth.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/master-data")
@RequiredArgsConstructor
public class MasterDataController {

    private final MasterDataService service;

    /*
     ============================================================
     CREATE APIs (ALL MASTER DATA)
     ============================================================
     */

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @PostMapping("/district")
    public MasterDataItemDTO createDistrict(
            @RequestBody CreateDistrictRequestDTO request
    ) {
        Long adminId = SecurityUtil.getCurrentUserId();
        return service.createDistrict(request, adminId);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @PostMapping("/facility-type")
    public MasterDataItemDTO createFacilityType(
            @RequestBody CreateFacilityTypeRequestDTO request
    ) {
        Long adminId = SecurityUtil.getCurrentUserId();
        return service.createFacilityType(request, adminId);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @PostMapping("/designation")
    public MasterDataItemDTO createDesignation(
            @RequestBody CreateDesignationRequestDTO request
    ) {
        Long adminId = SecurityUtil.getCurrentUserId();
        return service.createDesignation(request, adminId);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @PostMapping("/facility")
    public MasterDataItemDTO createFacility(
            @RequestBody CreateFacilityRequestDTO request
    ) {
        Long adminId = SecurityUtil.getCurrentUserId();
        return service.createFacility(request, adminId);
    }

    /*
     ============================================================
     CSV IMPORT
     ============================================================
     */

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @PostMapping(
            value = "/import/districts",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ImportResultDTO importDistricts(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        Long adminId = SecurityUtil.getCurrentUserId();

        return service.importDistricts(file, adminId);
    }

    /*
     ============================================================
     FETCH APIs
     ============================================================
     */

    @GetMapping
    public MasterDataResponseDTO getAllMasterData() {
        return service.getAllMasterData();
    }

    @GetMapping("/facilities/{districtId}")
    public List<MasterDataItemDTO> getFacilitiesByDistrict(
            @PathVariable Long districtId
    ) {
        return service.getFacilitiesByDistrict(districtId);
    }
}