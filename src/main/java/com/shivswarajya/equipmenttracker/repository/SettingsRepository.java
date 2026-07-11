package com.shivswarajya.equipmenttracker.repository;

import com.shivswarajya.equipmenttracker.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Optional<Settings> findTopByOrderByIdAsc();
}