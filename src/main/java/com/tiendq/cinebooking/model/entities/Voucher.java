package com.tiendq.cinebooking.model.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double voucherValue;

    private Instant startDay;

    private Instant endDay;
}
