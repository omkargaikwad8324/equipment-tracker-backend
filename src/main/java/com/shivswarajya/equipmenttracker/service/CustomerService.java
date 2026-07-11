package com.shivswarajya.equipmenttracker.service;

import com.shivswarajya.equipmenttracker.dto.request.CustomerDTO;
import com.shivswarajya.equipmenttracker.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(CustomerDTO dto);

    List<Customer> getAllCustomers();

    Customer getCustomer(Long id);

    Customer updateCustomer(Long id, CustomerDTO dto);

    void deleteCustomer(Long id);

    Customer getCustomerByMobile(String mobile);

    List<Customer> searchCustomers(String name);
}