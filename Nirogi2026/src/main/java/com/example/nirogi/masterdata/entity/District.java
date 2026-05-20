package com.example.nirogi.masterdata.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;


import javax.persistence.*;

@Entity
@Table(name = "district",
       uniqueConstraints = @UniqueConstraint(columnNames = "districtCode"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class District extends BaseMasterEntity {

    private String districtCode;
}
