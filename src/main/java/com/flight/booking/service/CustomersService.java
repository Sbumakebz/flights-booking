package com.flight.booking.service;


import com.flight.booking.dto.CustomerDTO;

import java.util.List;

public interface CustomersService {
    List<CustomerDTO> findAllCustomers();

    CustomerDTO findCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id);
}
