package com.shivswarajya.equipmenttracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shivswarajya.equipmenttracker.dto.request.DriverRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Driver;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.repository.DriverRepository;
import com.shivswarajya.equipmenttracker.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver addDriver(DriverRequestDTO dto) {

        if (driverRepository.findByDriverCode(dto.getDriverCode()).isPresent()) {
            throw new BadRequestException("Driver Code already exists.");
        }

        if (driverRepository.findByMobile(dto.getMobile()).isPresent()) {
            throw new BadRequestException("Mobile number already exists.");
        }

        if (driverRepository.findByLicenseNumber(dto.getLicenseNumber()).isPresent()) {
            throw new BadRequestException("License Number already exists.");
        }
        Driver driver = Driver.builder()
                .driverCode(dto.getDriverCode())
                .fullName(dto.getFullName())
                .mobile(dto.getMobile())
                .alternateMobile(dto.getAlternateMobile())
                .address(dto.getAddress())
                .aadhaarNumber(dto.getAadhaarNumber())
                .licenseNumber(dto.getLicenseNumber())
                .licenseExpiry(dto.getLicenseExpiry())
                .joiningDate(dto.getJoiningDate())
                .salary(dto.getSalary())
                .status(dto.getStatus())
                .remarks(dto.getRemarks())
                .build();

        return driverRepository.save(driver);
    }

    @Override
    public Driver updateDriver(Long id, DriverRequestDTO dto) {

        Driver driver = getDriver(id);
        if (!driver.getDriverCode().equals(dto.getDriverCode())
                && driverRepository.findByDriverCode(dto.getDriverCode()).isPresent()) {

            throw new BadRequestException("Driver Code already exists.");
        }

        if (!driver.getMobile().equals(dto.getMobile())
                && driverRepository.findByMobile(dto.getMobile()).isPresent()) {

            throw new BadRequestException("Mobile number already exists.");
        }

        if (!driver.getLicenseNumber().equals(dto.getLicenseNumber())
                && driverRepository.findByLicenseNumber(dto.getLicenseNumber()).isPresent()) {

            throw new BadRequestException("License Number already exists.");
        }
        driver.setDriverCode(dto.getDriverCode());
        driver.setFullName(dto.getFullName());
        driver.setMobile(dto.getMobile());
        driver.setAlternateMobile(dto.getAlternateMobile());
        driver.setAddress(dto.getAddress());
        driver.setAadhaarNumber(dto.getAadhaarNumber());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setLicenseExpiry(dto.getLicenseExpiry());
        driver.setJoiningDate(dto.getJoiningDate());
        driver.setSalary(dto.getSalary());
        driver.setStatus(dto.getStatus());
        driver.setRemarks(dto.getRemarks());

        return driverRepository.save(driver);
    }

    @Override
    public void deleteDriver(Long id) {

        Driver driver = getDriver(id);

        driverRepository.delete(driver);
    }

    @Override
    public Driver getDriver(Long id) {

        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with id : " + id));
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public List<Driver> searchDrivers(String keyword) {
        return driverRepository.findByFullNameContainingIgnoreCase(keyword);
    }
}