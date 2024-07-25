package com.flight.booking.controller;

import com.flight.booking.dto.CustomerDTO;
import com.flight.booking.model.Customer;
import com.flight.booking.service.CustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
@Tag(name = "Customers Controller", description = "Create, Update, Delete, Retrieve Customer data")
public class CustomersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersController.class);
    private final CustomersService customersService;

    @Autowired
    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @Operation(summary = "Get all customers", description = "Retrieve all customers")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all customers", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    @ApiResponse(responseCode = "204", description = "No customers exist", content = @Content())
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> findAllCustomers() {
        LOGGER.info("findAllCustomers Invoked");
        List<CustomerDTO> customers = customersService.findAllCustomers();
        return new ResponseEntity<>(customers, customers.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @Operation(summary = "Get a customer by ID", description = "Retrieve details for a specific customer by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of a customer", content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "204", description = "A customer with the specified ID does not exist", content = @Content())
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
        try {
            LOGGER.info("getCustomerById Invoked");
            CustomerDTO customer = customersService.findCustomerById(id);
            LOGGER.info("Retrieved customer name : {}", customer.name());
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }  catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Create Customer", description = "Create a new customer")
    @ApiResponse(responseCode = "201", description = "Successfully created of a customer", content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content())
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customer, BindingResult result) {
        LOGGER.info("createCustomer Invoked");
        CustomerDTO newCustomer = customersService.createCustomer(customer);
        LOGGER.info("Successfully created a customer");
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Customer", description = "Update an existing customer")
    @ApiResponse(responseCode = "200", description = "Successful update of a Customer", content = @Content(schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content())
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO Customer, BindingResult result) {
        LOGGER.info("updateCustomer Invoked : Id = {0} , Customer name = {1}", Customer.id(), Customer.name());
        CustomerDTO newCustomer = customersService.updateCustomer(Customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Customer", description = "Delete a customer")
    @ApiResponse(responseCode = "200", description = "Successfully deleted a Customer", content = @Content())
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful ", content = @Content())
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable long id) {
        LOGGER.info("deleteCustomer Invoked : Id = {}", id);
        customersService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
