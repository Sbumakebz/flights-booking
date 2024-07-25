package com.flight.booking.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Flight implements Serializable {

    private Long id;

    @Nonnull
    //@NotBlank(message = "Flight Number is a required field")
    private String flightNumber;

    @Nonnull
    private String origin;

    @Nonnull
    private String destination;

    @Nonnull
    private LocalDateTime departureDateAndTime;

    @Nonnull
    private LocalDateTime arrivalDateAndTime;

    @Nonnull
    private Integer availableSeats;

    @Nonnull
    private Double price;
}
