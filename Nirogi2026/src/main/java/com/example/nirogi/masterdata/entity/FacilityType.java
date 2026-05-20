package com.example.nirogi.masterdata.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "facility_type",
       uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FacilityType extends BaseMasterEntity {

    private String code;
}