package com.flight.booking.service.impl;

import com.flight.booking.dto.CustomerDTO;
import com.flight.booking.model.Customer;
import com.flight.booking.repository.CustomersRepository;
import com.flight.booking.service.CustomersService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CustomersServiceImpl implements CustomersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersServiceImpl.class);
    private final CustomersRepository customersRepository;

    public CustomersServiceImpl(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return convertListTDTOs(this.customersRepository.findAllCustomers());
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        Optional<Customer> customerOptional = this.customersRepository.findCustomerById(id);
        Customer customer = customerOptional.orElseThrow(() -> new EntityNotFoundException("Customer not found."));
        return convertToDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return convertToDTO(this.customersRepository.createCustomer(convertToEntity(customerDTO)));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        return convertToDTO(this.customersRepository.updateCustomer(convertToEntity(customerDTO)));
    }

    @Override
    public void deleteCustomer(Long id) {
        this.customersRepository.deleteCustomer(id);
    }

    private List<CustomerDTO> convertListTDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach(customer -> { customerDTOS.add(convertToDTO(customer));});

        return customerDTOS;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        if (dto.id() > 0) {
            customer.setId(dto.id());
        }
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());

        return customer;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhoneNumber());

        return customerDTO;
    }
}
