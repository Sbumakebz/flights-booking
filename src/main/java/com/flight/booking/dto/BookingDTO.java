package com.flight.booking.dto;

import java.time.LocalDateTime;

public record BookingDTO (Long id, Long customerID, String flightNumber, Integer numberOfSeats, LocalDateTime bookingDateAndTime) {
}
