package com.shivswarajya.equipmenttracker.service.impl;

import com.shivswarajya.equipmenttracker.dto.request.CustomerDTO;
import com.shivswarajya.equipmenttracker.entity.Customer;
import com.shivswarajya.equipmenttracker.repository.CustomerRepository;
import com.shivswarajya.equipmenttracker.service.CustomerService;
import org.springframework.stereotype.Service;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerByMobile(String mobile) {

        return customerRepository.findByMobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with mobile : " + mobile));
    }

    @Override
    public List<Customer> searchCustomers(String name) {

        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Customer addCustomer(CustomerDTO dto) {

        if (customerRepository.existsByMobile(dto.getMobile())) {
            throw new BadRequestException(
                    "Customer already exists with mobile number: " + dto.getMobile());
        }
        Customer customer = Customer.builder()
                .name(dto.getName())
                .mobile(dto.getMobile())
                .address(dto.getAddress())
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id : " + id));
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDTO dto) {

        Customer customer = getCustomer(id);
        
        if (!customer.getMobile().equals(dto.getMobile())
                && customerRepository.existsByMobile(dto.getMobile())) {

            throw new BadRequestException(
                    "Customer already exists with mobile number: " + dto.getMobile());
        }
        customer.setName(dto.getName());
        customer.setMobile(dto.getMobile());
        customer.setAddress(dto.getAddress());

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = getCustomer(id);

        customerRepository.delete(customer);
    }
}