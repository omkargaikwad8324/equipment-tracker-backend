package com.shivswarajya.equipmenttracker.mapper;

import com.shivswarajya.equipmenttracker.dto.response.CustomerResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDTO toResponse(Customer customer) {

        if (customer == null) {
            return null;
        }

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobile(customer.getMobile())
                .address(customer.getAddress())
                .build();
    }
}