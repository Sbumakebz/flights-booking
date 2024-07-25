package com.flight.booking.repository;

import com.flight.booking.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingsRepository {
    List<Booking> findAllBookings();

    Optional<Booking> findBookingById(Long id);

    Booking createBooking(Booking booking);

    Booking updateBooking(Booking booking);

    void deleteBooking(Long id);
}
