package com.example.nirogi.masterdata.mapper;


import com.example.nirogi.masterdata.dto.MasterDataItemDTO;
import com.example.nirogi.masterdata.entity.BaseMasterEntity;
import org.springframework.stereotype.Component;

@Component
public class MasterDataMapper {

    public MasterDataItemDTO toDTO(BaseMasterEntity entity) {

        if (entity == null) {
            return null;
        }

        return MasterDataItemDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
