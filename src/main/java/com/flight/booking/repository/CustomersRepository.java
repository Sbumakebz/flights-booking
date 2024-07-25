package com.flight.booking.repository;


import com.flight.booking.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomersRepository {
    List<Customer> findAllCustomers();

    Optional<Customer> findCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Long id);
}
