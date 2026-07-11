package com.shivswarajya.equipmenttracker.mapper;

import com.shivswarajya.equipmenttracker.dto.response.SettingsResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Settings;
import org.springframework.stereotype.Component;

@Component
public class SettingsMapper {

    public SettingsResponseDTO toResponse(Settings settings) {

        if (settings == null) {
            return null;
        }

        return SettingsResponseDTO.builder()
                .id(settings.getId())
                .companyName(settings.getCompanyName())
                .ownerName(settings.getOwnerName())
                .address(settings.getAddress())
                .mobile(settings.getMobile())
                .email(settings.getEmail())
                .website(settings.getWebsite())
                .gstNumber(settings.getGstNumber())
                .panNumber(settings.getPanNumber())
                .invoicePrefix(settings.getInvoicePrefix())
                .gstPercentage(settings.getGstPercentage())
                .currency(settings.getCurrency())
                .logo(settings.getLogo())
                .digitalSignature(settings.getDigitalSignature())
                .theme(settings.getTheme())
                .build();
    }
}