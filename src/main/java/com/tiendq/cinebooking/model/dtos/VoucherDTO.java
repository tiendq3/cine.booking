package com.tiendq.cinebooking.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoucherDTO {
    private String name;

    private String description;

    private Double voucherValue;

    private Instant startDay;

    private Instant endDay;
}
