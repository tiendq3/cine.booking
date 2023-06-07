package com.tiendq.cinebooking.model.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "showtimes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Showtime implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Film film;

    @ManyToOne
    private Room room;

    private Double price;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @ManyToOne
    private Cinema cinema;
}
