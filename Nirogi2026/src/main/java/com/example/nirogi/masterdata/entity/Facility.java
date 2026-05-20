package com.example.nirogi.masterdata.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "facility",
       uniqueConstraints = @UniqueConstraint(columnNames = "facilityCode"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Facility extends BaseMasterEntity {

    private Long districtId;

    private Long facilityTypeId;

    private String facilityCode;
}