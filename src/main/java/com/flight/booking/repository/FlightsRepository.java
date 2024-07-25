package com.flight.booking.repository;


import com.flight.booking.model.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightsRepository {
    List<Flight> findAllFlights();

    Optional<Flight> findFlightById(Long id);

    Flight createFlight(Flight booking);

    Flight updateFlight(Flight booking);

    void deleteFlight(Long id);
}
