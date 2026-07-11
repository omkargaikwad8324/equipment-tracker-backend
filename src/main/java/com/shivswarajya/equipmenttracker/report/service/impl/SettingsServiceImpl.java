package com.shivswarajya.equipmenttracker.report.service.impl;

import com.shivswarajya.equipmenttracker.dto.request.SettingsDTO;
import com.shivswarajya.equipmenttracker.entity.Settings;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.report.service.SettingsService;
import com.shivswarajya.equipmenttracker.repository.SettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;

    public SettingsServiceImpl(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    @Transactional
    public Settings initialize(SettingsDTO dto) {

        if (settingsRepository.count() > 0) {
            throw new BadRequestException(
                    "Settings already initialized.");
        }

        Settings settings = map(dto);

        return settingsRepository.save(settings);
    }

    @Override
    public Settings getSettings() {

        return settingsRepository.findTopByOrderByIdAsc()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Settings not found."));
    }

    @Override
    @Transactional
    public Settings updateSettings(SettingsDTO dto) {

        Settings settings = getSettings();

        settings.setCompanyName(dto.getCompanyName());
        settings.setOwnerName(dto.getOwnerName());
        settings.setAddress(dto.getAddress());
        settings.setMobile(dto.getMobile());
        settings.setEmail(dto.getEmail());
        settings.setWebsite(dto.getWebsite());
        settings.setGstNumber(dto.getGstNumber());
        settings.setPanNumber(dto.getPanNumber());
        settings.setInvoicePrefix(dto.getInvoicePrefix());
        settings.setGstPercentage(dto.getGstPercentage());
        settings.setCurrency(dto.getCurrency());
        settings.setLogo(dto.getLogo());
        settings.setDigitalSignature(dto.getDigitalSignature());
        settings.setTheme(dto.getTheme());

        return settingsRepository.save(settings);
    }

    private Settings map(SettingsDTO dto) {

        return Settings.builder()
                .companyName(dto.getCompanyName())
                .ownerName(dto.getOwnerName())
                .address(dto.getAddress())
                .mobile(dto.getMobile())
                .email(dto.getEmail())
                .website(dto.getWebsite())
                .gstNumber(dto.getGstNumber())
                .panNumber(dto.getPanNumber())
                .invoicePrefix(dto.getInvoicePrefix())
                .gstPercentage(dto.getGstPercentage())
                .currency(dto.getCurrency())
                .logo(dto.getLogo())
                .digitalSignature(dto.getDigitalSignature())
                .theme(dto.getTheme())
                .build();
    }
}