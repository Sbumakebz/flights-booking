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
public class Booking implements Serializable {

    private Long id;

    @Nonnull
    private Long customerID;

    @Nonnull
    //@NotBlank(message = "Flight Number is a required field")
    private String flightNumber;

    @Nonnull
    private Integer numberOfSeats;

    @Nonnull
    private LocalDateTime bookingDateAndTime;
}
