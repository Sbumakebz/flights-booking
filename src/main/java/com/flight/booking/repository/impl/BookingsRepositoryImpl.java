package com.flight.booking.repository.impl;


import com.flight.booking.model.Booking;
import com.flight.booking.repository.BookingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookingsRepositoryImpl implements BookingsRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingsRepositoryImpl.class);

    private Map<Long, Booking> bookings = new ConcurrentHashMap<>();
    @Override
    public List<Booking> findAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    @Override
    public Optional<Booking> findBookingById(Long id) {
        return bookings.get(id) != null ? Optional.of(bookings.get(id)) : Optional.empty();
    }

    @Override
    public Booking createBooking(Booking booking) {
        return bookings.put(booking.getId(), booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return bookings.put(booking.getId(), booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookings.remove(id);
    }
}
