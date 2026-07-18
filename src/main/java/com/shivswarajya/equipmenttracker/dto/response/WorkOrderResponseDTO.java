package com.shivswarajya.equipmenttracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderResponseDTO {

    private Long id;

    private String workOrderNo;

    private String customerName;

    private LocalDate workDate;

    private String siteName;

    private String workDescription;

    private String remarks;

    private WorkStatus status;

    private BigDecimal grandTotal;
    

    private List<WorkOrderItemResponseDTO> items;

}