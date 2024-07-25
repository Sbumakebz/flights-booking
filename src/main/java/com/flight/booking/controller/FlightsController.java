package com.flight.booking.controller;

import com.flight.booking.dto.FlightDTO;
import com.flight.booking.model.Flight;
import com.flight.booking.service.FlightsService;
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
@RequestMapping("flights")
@Tag(name = "Flights Controller", description = "Create, Update, Delete, Retrieve Flights data")
public class FlightsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightsController.class);
    private final FlightsService flightsService;

    @Autowired
    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @Operation(summary = "Get all flights", description = "Provides a list of all flights")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all flights", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Flight.class))))
    @ApiResponse(responseCode = "204", description = "No flights exist", content = @Content())
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FlightDTO>> findAllFlights() {
        LOGGER.info("getAllFlights Invoked");
        List<FlightDTO> flights = flightsService.findAllFlights();
        return new ResponseEntity<>(flights, flights.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @Operation(summary = "Get a flight by ID", description = "Retrieve details for a specific flight by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of a flight", content = @Content(schema = @Schema(implementation = Flight.class)))
    @ApiResponse(responseCode = "204", description = "A Flight with the specified ID does not exist", content = @Content())
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightDTO> findFlightById(@PathVariable Long id) {
        try {
            LOGGER.info("getFlightById Invoked");
            FlightDTO flight = flightsService.findFlightById(id);
            LOGGER.info("Retrieved Flight number : {}", flight.flightNumber());
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Create Flight", description = "Create a new flight")
    @ApiResponse(responseCode = "201", description = "Successfully created of a Flight", content = @Content(schema = @Schema(implementation = Flight.class)))
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content())
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flight, BindingResult result) {
        LOGGER.info("createFlight Invoked");
        FlightDTO newFlight = flightsService.createFlight(flight);
        LOGGER.info("Successfully created Flight");
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Flight", description = "Update an existing flight")
    @ApiResponse(responseCode = "200", description = "Successful update of a Flight", content = @Content(schema = @Schema(implementation = Flight.class)))
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content())
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightDTO> updateFlight(@Valid @RequestBody FlightDTO flight, BindingResult result) {
        LOGGER.info("updateFlight Invoked : Id = {0} , Flight number = {1}", flight.id(), flight.flightNumber());
        FlightDTO newFlight = flightsService.updateFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Flight", description = "Delete a flight")
    @ApiResponse(responseCode = "200", description = "Successfully deleted a Flight", content = @Content())
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful ", content = @Content())
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteFlight(@PathVariable long id) {
        LOGGER.info("deleteFlight Invoked : Id = {}", id);
        flightsService.deleteFlight(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
