package com.flight.booking.repository.impl;


import com.flight.booking.model.Customer;
import com.flight.booking.repository.CustomersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomersRepositoryImpl implements CustomersRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersRepositoryImpl.class);

    private Map<Long, Customer> customers = new HashMap<>();
    @Override
    public List<Customer> findAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customers.get(id) != null ? Optional.of(customers.get(id)) : Optional.empty();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customers.put(customer.getId(), customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customers.put(customer.getId(), customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customers.remove(id);
    }
}
