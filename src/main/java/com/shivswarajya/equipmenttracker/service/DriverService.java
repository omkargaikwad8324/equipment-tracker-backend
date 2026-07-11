package com.shivswarajya.equipmenttracker.service;

import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.DriverRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Driver;

public interface DriverService {

    Driver addDriver(DriverRequestDTO dto);

    Driver updateDriver(Long id, DriverRequestDTO dto);

    void deleteDriver(Long id);

    Driver getDriver(Long id);

    List<Driver> getAllDrivers();

    List<Driver> searchDrivers(String keyword);

}