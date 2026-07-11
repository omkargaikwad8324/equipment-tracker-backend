package com.shivswarajya.equipmenttracker.controller;

import com.shivswarajya.equipmenttracker.dto.request.CustomerDTO;
import com.shivswarajya.equipmenttracker.dto.response.CustomerResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Customer;
import com.shivswarajya.equipmenttracker.mapper.CustomerMapper;
import com.shivswarajya.equipmenttracker.service.CustomerService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

        private final CustomerService customerService;
        private final CustomerMapper customerMapper;

        public CustomerController(CustomerService customerService,
                        CustomerMapper customerMapper) {

                this.customerService = customerService;
                this.customerMapper = customerMapper;
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ApiResponse<CustomerResponseDTO> addCustomer(
                        @Valid @RequestBody CustomerDTO dto) {

                Customer customer = customerService.addCustomer(dto);

                return new ApiResponse<>(
                                true,
                                "Customer added successfully",
                                customerMapper.toResponse(customer));
        }

        @GetMapping("/search")
        @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
        public ApiResponse<List<CustomerResponseDTO>> searchCustomers(
                        @RequestParam String name) {

                List<CustomerResponseDTO> response = customerService.searchCustomers(name)
                                .stream()
                                .map(customerMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Customers fetched successfully",
                                response);
        }

        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
        public ApiResponse<List<CustomerResponseDTO>> getAllCustomers() {
                List<CustomerResponseDTO> response = customerService.getAllCustomers()
                                .stream()
                                .map(customerMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Customers fetched successfully",
                                response);
        }

        @GetMapping("/mobile/{mobile}")
        @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
        public ApiResponse<CustomerResponseDTO> getByMobile(
                        @PathVariable String mobile) {
                return new ApiResponse<>(
                                true,
                                "Customer fetched successfully",
                                customerMapper.toResponse(
                                                customerService.getCustomerByMobile(mobile)));
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
        public ApiResponse<CustomerResponseDTO> getCustomer(
                        @PathVariable Long id) {
                Customer customer = customerService.getCustomer(id);

                return new ApiResponse<>(
                                true,
                                "Customer fetched successfully",
                                customerMapper.toResponse(customer));
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ApiResponse<CustomerResponseDTO> updateCustomer(
                        @PathVariable Long id,
                        @Valid @RequestBody CustomerDTO dto) {

                Customer customer = customerService.updateCustomer(id, dto);

                return new ApiResponse<>(
                                true,
                                "Customer updated successfully",
                                customerMapper.toResponse(customer));
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ApiResponse<String> deleteCustomer(@PathVariable Long id) {

                customerService.deleteCustomer(id);

                return new ApiResponse<>(
                                true,
                                "Customer deleted successfully",
                                null);
        }
}