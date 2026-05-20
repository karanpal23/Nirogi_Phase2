package com.example.nirogi.masterdata.serviceImpl;


import com.example.nirogi.masterdata.dto.*;
import com.example.nirogi.masterdata.entity.*;
import com.example.nirogi.masterdata.repository.*;
import com.example.nirogi.masterdata.mapper.MasterDataMapper;
import com.example.nirogi.masterdata.importer.MasterDataCsvImporter;
import com.example.nirogi.masterdata.util.CodeGenerator;
import com.example.nirogi.masterdata.service.MasterDataService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final DistrictRepository districtRepo;
    private final FacilityRepository facilityRepo;
    private final FacilityTypeRepository facilityTypeRepo;
    private final DesignationRepository designationRepo;

    private final MasterDataMapper mapper;
    private final CodeGenerator codeGenerator;
    private final MasterDataCsvImporter importer;

    /*
     ============================================================
     CREATE DISTRICT
     ============================================================
     */
    @Override
    public MasterDataItemDTO createDistrict(
            CreateDistrictRequestDTO req,
            Long adminId
    ) {

        String name = req.getName().trim();

        if (districtRepo.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("District already exists");
        }

        long seq = districtRepo.count() + 1;

        String code = codeGenerator.generateDistrictCode(name, seq);

        while (districtRepo.existsByDistrictCode(code)) {
            seq++;
            code = codeGenerator.generateDistrictCode(name, seq);
        }

        District d = District.builder()
                .name(name)
                .districtCode(code)
                .createdBy(adminId)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        districtRepo.save(d);

        return mapper.toDTO(d);
    }

    /*
     ============================================================
     CREATE FACILITY TYPE
     ============================================================
     */
    @Override
    public MasterDataItemDTO createFacilityType(
            CreateFacilityTypeRequestDTO req,
            Long adminId
    ) {

        String name = req.getName().trim();

        if (facilityTypeRepo.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("FacilityType already exists");
        }

        String code = req.getCode().toUpperCase().trim();

        FacilityType ft = FacilityType.builder()
                .name(name)
                .code(code)
                .createdBy(adminId)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        facilityTypeRepo.save(ft);

        return mapper.toDTO(ft);
    }

    /*
     ============================================================
     CREATE DESIGNATION
     ============================================================
     */
    @Override
    public MasterDataItemDTO createDesignation(
            CreateDesignationRequestDTO req,
            Long adminId
    ) {

        String name = req.getName().trim();

        if (designationRepo.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("Designation already exists");
        }

        Designation d = Designation.builder()
                .name(name)
                .description(req.getDescription())
                .createdBy(adminId)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        designationRepo.save(d);

        return mapper.toDTO(d);
    }

    /*
     ============================================================
     CREATE FACILITY
     ============================================================
     */
    @Override
    public MasterDataItemDTO createFacility(
            CreateFacilityRequestDTO req,
            Long adminId
    ) {

        District district = districtRepo.findById(req.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        FacilityType ft = facilityTypeRepo.findById(req.getFacilityTypeId())
                .orElseThrow(() -> new RuntimeException("FacilityType not found"));

        long seq = facilityRepo.count() + 1;

        String code = codeGenerator.generateFacilityCode(
                ft.getCode(),
                district.getDistrictCode(),
                seq
        );

        while (facilityRepo.existsByFacilityCode(code)) {
            seq++;
            code = codeGenerator.generateFacilityCode(
                    ft.getCode(),
                    district.getDistrictCode(),
                    seq
            );
        }

        Facility f = Facility.builder()
                .name(req.getName())
                .districtId(req.getDistrictId())
                .facilityTypeId(req.getFacilityTypeId())
                .facilityCode(code)
                .createdBy(adminId)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        facilityRepo.save(f);

        return mapper.toDTO(f);
    }

    /*
     ============================================================
     IMPORT DISTRICT
     ============================================================
     */
    @Override
    public ImportResultDTO importDistricts(
            MultipartFile file,
            Long adminId
    ) throws Exception {

        return importer.importDistricts(file, adminId);
    }

    /*
     ============================================================
     FETCH ALL MASTER DATA
     ============================================================
     */
    @Override
    public MasterDataResponseDTO getAllMasterData() {

        return MasterDataResponseDTO.builder()
                .districts(
                        districtRepo.findAll()
                                .stream()
                                .map(mapper::toDTO)
                                .collect(Collectors.toList())
                )
                .facilityTypes(
                        facilityTypeRepo.findAll()
                                .stream()
                                .map(mapper::toDTO)
                                .collect(Collectors.toList())
                )
                .designations(
                        designationRepo.findAll()
                                .stream()
                                .map(mapper::toDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    /*
     ============================================================
     GET FACILITIES BY DISTRICT
     ============================================================
     */
    @Override
    public List<MasterDataItemDTO> getFacilitiesByDistrict(Long districtId) {

        return facilityRepo
                .findByDistrictIdAndIsActiveTrue(districtId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}