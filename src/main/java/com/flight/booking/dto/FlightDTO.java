package com.flight.booking.dto;

import java.time.LocalDateTime;

public record FlightDTO( Long id, String flightNumber, String origin, String destination, LocalDateTime departureDateAndTime, LocalDateTime arrivalDateAndTime,  Integer availableSeats, Double price) {
}
