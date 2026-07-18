package com.shivswarajya.equipmenttracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkOrderSummaryDTO {

    private Long id;

    private String workOrderNo;

    private LocalDate workDate;

    private String customerName;

    private String siteName;

    private Integer totalEquipment;

    private BigDecimal grandTotal;

    private WorkStatus status;

}