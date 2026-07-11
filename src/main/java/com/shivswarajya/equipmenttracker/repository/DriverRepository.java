package com.shivswarajya.equipmenttracker.repository;

import com.shivswarajya.equipmenttracker.entity.Driver;
import com.shivswarajya.equipmenttracker.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByDriverCode(String driverCode);

    Optional<Driver> findByMobile(String mobile);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    List<Driver> findByFullNameContainingIgnoreCase(String fullName);

    List<Driver> findByStatus(DriverStatus status);
}