package com.flight.booking;

import com.flight.booking.dto.FlightDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FlightsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findAllFlights() {
        ResponseEntity<List<FlightDTO>> bookCreationResult = this.restTemplate.exchange("http://localhost:8081/bookings", HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDTO>>(){});
        Assertions.assertTrue(!bookCreationResult.getBody().isEmpty());
    }

    @Test
    public void findFlightByID() {
        ResponseEntity<FlightDTO> result = restTemplate
                .exchange("http://localhost:8081/booking/" + 1L, HttpMethod.GET, null, FlightDTO.class);

        Assertions.assertNotNull(result.getBody());
    }

    @Test
    public void findFlightByIDNotFound() throws Exception {
        ResponseEntity<FlightDTO> result = restTemplate
                .exchange("http://localhost:8081/booking/" + 2L, HttpMethod.GET, null, FlightDTO.class);

        Assertions.assertEquals("jakarta.persistence.EntityNotFoundException: Flight not found", result.getBody());
    }

    @Test
    public void createFlight() {
        FlightDTO flightDTO = new FlightDTO(2L, "Flight01", "Nania", "Wonderland",  LocalDateTime.now(), LocalDateTime.now(), 10, 111.0 );
        ResponseEntity<FlightDTO> result = restTemplate
                .postForEntity("http://localhost:8081/booking", flightDTO, FlightDTO.class);

        Assertions.assertNotNull(result.getBody());
    }

    @Test
    public void updateFlight() {
        FlightDTO flightDTO = new FlightDTO(1L, "Flight01", "Nania", "Wonderland",  LocalDateTime.now(), LocalDateTime.now(), 10, 111.0 );
        HttpEntity<FlightDTO> request = new HttpEntity<>(flightDTO);

        ResponseEntity<FlightDTO> result = restTemplate
                .exchange("http://localhost:8081/booking", HttpMethod.PUT, request, FlightDTO.class);

        Assertions.assertNotNull(result.getBody());
    }

    @Test
    public void deleteFlight() {
        FlightDTO flightDTO = new FlightDTO(1L, "Flight01", "Nania", "Wonderland",  LocalDateTime.now(), LocalDateTime.now(), 10, 111.0 );

        ResponseEntity<FlightDTO> result = restTemplate
                .exchange("http://localhost:8081/booking/" + 2L, HttpMethod.DELETE, null, FlightDTO.class);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}