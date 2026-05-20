package com.example.nirogi.beneficiary.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import javax.persistence.*;


@Entity
@Table(
        name = "beneficiary",
        indexes = {

                @Index(
                        name = "idx_member_id",
                        columnList = "memberId"
                ),

                @Index(
                        name = "idx_ppp_id",
                        columnList = "pppId"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String memberId;

    private String pppId;

    private String firstName;

    private String lastName;

    private String fatherHusbandFirstname;

    private String fatherHusbandLastname;

    private String blockTownCity;

    private String wardVillage;

    private String district;

    private String districtLgd;

    private String blockLgd;

    private String wardLgd;

    private Double income;

    private Integer age;

    private LocalDate dateOfBirth;

    private String gender;

    private String mobileNo;

    @Column(length = 2000)
    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}