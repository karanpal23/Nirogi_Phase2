package com.example.nirogi.masterdata.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CsvParserUtil {

    public List<String[]> parse(MultipartFile file) throws Exception {

        List<String[]> rows = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream())
        );

        String line;
        boolean skip = true;

        while ((line = reader.readLine()) != null) {

            if (skip) {
                skip = false;
                continue;
            }

            rows.add(line.split(","));
        }

        return rows;
    }
}