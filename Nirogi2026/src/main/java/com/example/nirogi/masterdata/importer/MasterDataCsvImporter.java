package com.example.nirogi.masterdata.importer;


import com.example.nirogi.masterdata.dto.ImportResultDTO;
import com.example.nirogi.masterdata.entity.Designation;
import com.example.nirogi.masterdata.entity.District;
import com.example.nirogi.masterdata.repository.DesignationRepository;
import com.example.nirogi.masterdata.repository.DistrictRepository;
import com.example.nirogi.masterdata.util.CodeGenerator;
import com.example.nirogi.masterdata.util.CsvParserUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MasterDataCsvImporter {

    private final CsvParserUtil parser;
    private final DistrictRepository districtRepo;
    private final CodeGenerator codeGenerator;

    public ImportResultDTO importDistricts(
            MultipartFile file,
            Long adminId
    ) throws Exception {

        List<String[]> rows = parser.parse(file);

        int success = 0;
        List<String> errors = new ArrayList<>();

        for (String[] row : rows) {

            try {

                String name = row[0].trim();

                if (districtRepo.existsByNameIgnoreCase(name)) {
                    continue;
                }

                long seq = districtRepo.count() + 1;

                String code =
                        codeGenerator.generateDistrictCode(name, seq);

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

                success++;

            } catch (Exception ex) {

                errors.add(Arrays.toString(row));
            }
        }

        return ImportResultDTO.builder()
                .totalRecords(rows.size())
                .successCount(success)
                .failedCount(rows.size() - success)
                .errors(errors)
                .build();
    }
}