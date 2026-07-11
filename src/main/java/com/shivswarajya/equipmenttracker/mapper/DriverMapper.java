package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.DriverResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Driver;

@Component
public class DriverMapper {

    public DriverResponseDTO toResponse(Driver driver) {

        if (driver == null) {
            return null;
        }

        return DriverResponseDTO.builder()
                .id(driver.getId())
                .driverCode(driver.getDriverCode())
                .fullName(driver.getFullName())
                .mobile(driver.getMobile())
                .alternateMobile(driver.getAlternateMobile())
                .address(driver.getAddress())
                .aadhaarNumber(driver.getAadhaarNumber())
                .licenseNumber(driver.getLicenseNumber())
                .licenseExpiry(driver.getLicenseExpiry())
                .joiningDate(driver.getJoiningDate())
                .salary(driver.getSalary())
                .status(driver.getStatus())
                .remarks(driver.getRemarks())
                .build();
    }
}