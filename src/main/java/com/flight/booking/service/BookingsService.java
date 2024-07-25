package com.flight.booking.service;


import com.flight.booking.dto.BookingDTO;
import com.flight.booking.model.Booking;

import java.util.List;

public interface BookingsService {
    List<BookingDTO> findAllBookings();

    BookingDTO findBookingById(Long id);

    BookingDTO createBooking(BookingDTO booking);

    BookingDTO updateBooking(BookingDTO booking);

    void deleteBooking(Long id);
}
