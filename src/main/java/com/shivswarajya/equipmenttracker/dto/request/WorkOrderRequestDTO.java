package com.shivswarajya.equipmenttracker.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkOrderRequestDTO {

    @NotNull(message = "Customer is required")
    private Long customerId;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;

    @NotBlank(message = "Site name is required")
    private String siteName;

    private String workDescription;

    private String remarks;

    private WorkStatus status;

    @Valid
    @NotNull(message = "At least one work item is required")
    private List<WorkOrderItemRequestDTO> items;

    

}