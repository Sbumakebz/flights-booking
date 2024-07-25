package com.flight.booking.service.impl;


import com.flight.booking.dto.BookingDTO;
import com.flight.booking.model.Booking;
import com.flight.booking.repository.BookingsRepository;
import com.flight.booking.service.BookingsService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookingsServiceImpl implements BookingsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingsServiceImpl.class);

    private final BookingsRepository bookingsRepository;

    public BookingsServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public List<BookingDTO> findAllBookings() {
        return convertListTDTOs(this.bookingsRepository.findAllBookings());
    }

    @Override
    public BookingDTO findBookingById(Long id) {
        Optional<Booking> bookingOptional = this.bookingsRepository.findBookingById(id);
        Booking booking = bookingOptional.orElseThrow(() -> new EntityNotFoundException("Booking not found."));
        return convertToDTO(booking);
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        return convertToDTO(this.bookingsRepository.createBooking(convertToEntity(bookingDTO)));
    }

    @Override
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        return convertToDTO(this.bookingsRepository.updateBooking(convertToEntity(bookingDTO)));
    }

    @Override
    public void deleteBooking(Long id) {
        this.bookingsRepository.deleteBooking(id);
    }

    private List<BookingDTO> convertListTDTOs(List<Booking> bookings) {
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(booking -> { bookingDTOS.add(convertToDTO(booking));});

        return bookingDTOS;
    }

    private Booking convertToEntity(BookingDTO dto) {
        Booking booking = new Booking();
        if (dto.id() > 0) {
            booking.setId(dto.id());
        }
        booking.setCustomerID(dto.customerID());
        booking.setFlightNumber(dto.flightNumber());
        booking.setNumberOfSeats(dto.numberOfSeats());
        booking.setBookingDateAndTime(dto.bookingDateAndTime());
        return booking;
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO(booking.getId(), booking.getCustomerID(), booking.getFlightNumber(),
                booking.getNumberOfSeats(), booking.getBookingDateAndTime());

        return bookingDTO;
    }
}
