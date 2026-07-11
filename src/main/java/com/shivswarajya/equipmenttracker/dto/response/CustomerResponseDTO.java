package com.shivswarajya.equipmenttracker.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDTO {

    private Long id;

    private String name;

    private String mobile;

    private String address;
}
