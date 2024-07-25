package com.flight.booking.controller;

import com.flight.booking.dto.BookingDTO;
import com.flight.booking.model.Booking;
import com.flight.booking.service.BookingsService;
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
@RequestMapping("bookings")
@Tag(name = "Bookings Controller", description = "Create, Update, Delete, Retrieve Bookings data")
public class BookingsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingsController.class);
    private final BookingsService bookingsService;

    @Autowired
    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @Operation(summary = "Get all bookings", description = "Retrieve all bookings")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all bookings", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Booking.class))))
    @ApiResponse(responseCode = "204", description = "No bookings exist", content = @Content())
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookingDTO>> findAllBookings() {
        LOGGER.info("findAllBookings Invoked");
        List<BookingDTO> bookings = bookingsService.findAllBookings();
        return new ResponseEntity<>(bookings, bookings.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @Operation(summary = "Get a booking by ID", description = "Retrieve details for a specific booking by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of a booking", content = @Content(schema = @Schema(implementation = Booking.class)))
    @ApiResponse(responseCode = "204", description = "A booking with the specified ID does not exist", content = @Content())
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> findBookingById(@PathVariable Long id) throws EntityNotFoundException {
        try {
            LOGGER.info("findBookingById Invoked");
            BookingDTO booking = bookingsService.findBookingById(id);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }  catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Create booking", description = "Create a new booking")
    @ApiResponse(responseCode = "201", description = "Successfully created a booking", content = @Content(schema = @Schema(implementation = Booking.class)))
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content())
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO booking, BindingResult result) {
        LOGGER.info("createBooking Invoked");
        BookingDTO newBooking = bookingsService.createBooking(booking);
        LOGGER.info("Successfully created booking");
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @Operation(summary = "Update booking", description = "Update an existing booking")
    @ApiResponse(responseCode = "200", description = "Successful update of a booking", content = @Content(schema = @Schema(implementation = Booking.class)))
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content())
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> updateBooking(@Valid @RequestBody BookingDTO booking, BindingResult result) {
        LOGGER.info("updateBooking Invoked : Id = {}", booking.id());
        BookingDTO newBooking = bookingsService.updateBooking(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Booking", description = "Cancel a booking")
    @ApiResponse(responseCode = "200", description = "Successfully deleted a booking", content = @Content())
    @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful ", content = @Content())
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable long id) {
        LOGGER.info("deleteBooking Invoked : Id = {}", id);
        bookingsService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
