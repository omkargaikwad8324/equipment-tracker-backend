package com.shivswarajya.equipmenttracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotBlank(message = "Customer name is required")
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must contain 10 digits")
    private String mobile;

    private String address;
}