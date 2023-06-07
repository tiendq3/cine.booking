package com.tiendq.cinebooking.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingRequest {
    @NotNull
    private String[] seatNames;

    @NotNull
    private Long showtimeId;
}
