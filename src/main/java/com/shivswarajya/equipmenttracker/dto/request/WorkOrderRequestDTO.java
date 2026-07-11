package com.shivswarajya.equipmenttracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;

@Data
public class WorkOrderRequestDTO {

    

    @NotNull
    private Long customerId;

    @NotNull
    private Long equipmentId;

    @NotNull
    private Long driverId;

    @NotNull
    private LocalDate workDate;

    @NotBlank
    private String siteName;

    private String workDescription;

    @NotNull
    private Double startMeter;

    @NotNull
    private Double endMeter;

    private Double dieselUsed;

    private WorkStatus status;

    private String remarks;
}