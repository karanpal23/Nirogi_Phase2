package com.example.nirogi.masterdata.dto;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterDataResponseDTO {

    private List<MasterDataItemDTO> designations;

    private List<MasterDataItemDTO> facilityTypes;

    private List<MasterDataItemDTO> districts;
}
