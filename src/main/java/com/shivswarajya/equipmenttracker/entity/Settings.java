package com.shivswarajya.equipmenttracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private String ownerName;

    @Column(length = 500)
    private String address;

    private String mobile;

    private String email;

    private String website;

    private String gstNumber;

    private String panNumber;

    @Column(nullable = false)
    private String invoicePrefix;

    @Column(nullable = false)
    private Double gstPercentage;

    @Column(nullable = false)
    private String currency;

    @Lob
    private String logo;

    @Lob
    private String digitalSignature;

    private String theme;
}