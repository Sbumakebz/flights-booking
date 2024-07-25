package com.flight.booking.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Serializable {

    private Long id;

    @Nonnull
    //@NotBlank(message = "Customer name is a required field")
    private String name;

    @Nonnull
    private String email;

    @Nonnull
    private String phoneNumber;
}
