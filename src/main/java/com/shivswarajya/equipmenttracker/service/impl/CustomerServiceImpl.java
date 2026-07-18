package com.shivswarajya.equipmenttracker.service.impl;

import com.shivswarajya.equipmenttracker.dto.request.CustomerDTO;
import com.shivswarajya.equipmenttracker.entity.Customer;
import com.shivswarajya.equipmenttracker.entity.Invoice;
import com.shivswarajya.equipmenttracker.repository.CustomerRepository;
import com.shivswarajya.equipmenttracker.service.CustomerService;
import org.springframework.stereotype.Service;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import java.util.List;
import com.shivswarajya.equipmenttracker.dto.response.CustomerSummaryDTO;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;
import com.shivswarajya.equipmenttracker.repository.InvoiceRepository;
import com.shivswarajya.equipmenttracker.repository.PaymentRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final WorkOrderRepository workOrderRepository;

    private final InvoiceRepository invoiceRepository;

    private final PaymentRepository paymentRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, WorkOrderRepository workOrderRepository,
            InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.customerRepository = customerRepository;
        this.workOrderRepository = workOrderRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
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

    @Override
    public CustomerSummaryDTO getCustomerSummary(Long customerId) {

        Customer customer = getCustomer(customerId);

        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);

        long totalWorkOrders = workOrderRepository.findByCustomerId(customerId).size();

        long invoiceCount = invoices.size();

        double totalRevenue = invoices.stream()
                .mapToDouble(invoice -> invoice.getGrandTotal() == null ? 0.0 : invoice.getGrandTotal())
                .sum();

        double paidAmount = invoices.stream()
                .mapToDouble(invoice -> invoice.getPaidAmount() == null ? 0.0 : invoice.getPaidAmount())
                .sum();

        double pendingAmount = invoices.stream()
                .mapToDouble(invoice -> invoice.getBalanceAmount() == null ? 0.0 : invoice.getBalanceAmount())
                .sum();

        long paymentCount = invoices.stream()
                .mapToLong(invoice -> invoice.getPayments().size())
                .sum();

        return CustomerSummaryDTO.builder()
                .customerId(customer.getId())
                .customerName(customer.getName())
                .totalRevenue(totalRevenue)
                .paidAmount(paidAmount)
                .pendingAmount(pendingAmount)
                .totalWorkOrders(totalWorkOrders)
                .invoiceCount(invoiceCount)
                .paymentCount(paymentCount)
                .build();
    }
}