package com.flight.booking.service.impl;

import com.flight.booking.dto.FlightDTO;
import com.flight.booking.model.Flight;
import com.flight.booking.repository.FlightsRepository;
import com.flight.booking.service.FlightsService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightsServiceImpl implements FlightsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingsServiceImpl.class);
    private final FlightsRepository flightsRepository;

    public FlightsServiceImpl(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @Override
    public List<FlightDTO> findAllFlights() {
        return convertListTDTOs(this.flightsRepository.findAllFlights());
    }

    @Override
    public FlightDTO findFlightById(Long id) {
        Optional<Flight> flightOptional = this.flightsRepository.findFlightById(id);
        Flight flight = flightOptional.orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        return convertToDTO(flight);
    }

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {
        return convertToDTO(this.flightsRepository.createFlight(convertToEntity(flightDTO)));
    }

    @Override
    public FlightDTO updateFlight(FlightDTO flightDTO) {
        return convertToDTO(this.flightsRepository.updateFlight(convertToEntity(flightDTO)));
    }

    @Override
    public void deleteFlight(Long id) {
        this.flightsRepository.deleteFlight(id);
    }

    private List<FlightDTO> convertListTDTOs(List<Flight> flights) {
        List<FlightDTO> flightDTOS = new ArrayList<>();
        flights.forEach(booking -> { flightDTOS.add(convertToDTO(booking));});

        return flightDTOS;
    }

    private Flight convertToEntity(FlightDTO dto) {
        Flight flight = new Flight();
        if (dto.id() > 0) {
            flight.setId(dto.id());
        }
        flight.setFlightNumber(dto.flightNumber());
        flight.setOrigin(dto.origin());
        flight.setDestination(dto.destination());
        flight.setDepartureDateAndTime(dto.departureDateAndTime());
        flight.setOrigin(dto.origin());
        flight.setArrivalDateAndTime(dto.arrivalDateAndTime());
        flight.setAvailableSeats(dto.availableSeats());
        flight.setPrice(dto.price());
        return flight;
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO(flight.getId(), flight.getFlightNumber(), flight.getOrigin(),
                flight.getDestination(), flight.getDepartureDateAndTime(),
                flight.getArrivalDateAndTime(), flight.getAvailableSeats(), flight.getPrice());

        return flightDTO;
    }
}
