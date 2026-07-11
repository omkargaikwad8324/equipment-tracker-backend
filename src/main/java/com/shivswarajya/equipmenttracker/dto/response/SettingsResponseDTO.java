package com.shivswarajya.equipmenttracker.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsResponseDTO {

    private Long id;
    private String companyName;
    private String ownerName;
    private String address;
    private String mobile;
    private String email;
    private String website;
    private String gstNumber;
    private String panNumber;
    private String invoicePrefix;
    private Double gstPercentage;
    private String currency;
    private String logo;
    private String digitalSignature;
    private String theme;
}