package com.shivswarajya.equipmenttracker.report.service;

import com.shivswarajya.equipmenttracker.dto.request.SettingsDTO;
import com.shivswarajya.equipmenttracker.entity.Settings;

public interface SettingsService {

    Settings initialize(SettingsDTO dto);

    Settings getSettings();

    Settings updateSettings(SettingsDTO dto);
}