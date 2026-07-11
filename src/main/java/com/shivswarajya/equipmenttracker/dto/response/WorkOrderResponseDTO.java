package com.shivswarajya.equipmenttracker.dto.response;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class WorkOrderResponseDTO {

    private Long id;

    private String workOrderNo;

    private String customerName;

    private String equipmentName;

    private String driverName;

    private LocalDate workDate;

    private String siteName;

    private String workDescription;

    private Double startMeter;

    private Double endMeter;

    private Double totalHours;

    private BigDecimal hourlyRate;

    private BigDecimal totalAmount;

    private Double dieselUsed;

    private WorkStatus status;

    private String remarks;
}