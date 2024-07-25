package com.flight.booking.repository.impl;


import com.flight.booking.model.Flight;
import com.flight.booking.repository.FlightsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Repository
public class FlightsRepositoryImpl implements FlightsRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightsRepositoryImpl.class);

    private Map<Long, Flight> flights = Map.of(1L, new Flight(1L, "FAC301", "Cape Town", "Durban",
            LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(), LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(),
            12, 987.0));
    @Override
    public List<Flight> findAllFlights() {
        return new ArrayList<>(flights.values());
    }

    @Override
    public Optional<Flight> findFlightById(Long id) {
        return flights.get(id) != null ? Optional.of(flights.get(id)) : Optional.empty();
    }

    @Override
    public Flight createFlight(Flight flight) {
        return flights.put(flight.getId(), flight);
    }

    @Override
    public Flight updateFlight(Flight flight) {
        return flights.put(flight.getId(), flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flights.remove(id);
    }
}
