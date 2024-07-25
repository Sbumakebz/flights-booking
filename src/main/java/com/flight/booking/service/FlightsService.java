package com.flight.booking.service;


import com.flight.booking.dto.FlightDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface FlightsService {
    List<FlightDTO> findAllFlights();

    FlightDTO findFlightById(Long id);

    FlightDTO createFlight(FlightDTO flightDTO);

    FlightDTO updateFlight(FlightDTO flightDTO);

    void deleteFlight(Long id);
}
