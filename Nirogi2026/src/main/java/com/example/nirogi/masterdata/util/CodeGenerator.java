package com.example.nirogi.masterdata.util;


import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {

    public String generateDistrictCode(String name, long seq) {

        String prefix = name.replaceAll("[^A-Za-z]", "")
                .toUpperCase();

        prefix = prefix.length() >= 3
                ? prefix.substring(0, 3)
                : prefix;

        return prefix + String.format("%03d", seq);
    }

    public String generateFacilityCode(
            String typeCode,
            String districtCode,
            long seq
    ) {

        return typeCode + "-"
                + districtCode + "-"
                + String.format("%04d", seq);
    }
}