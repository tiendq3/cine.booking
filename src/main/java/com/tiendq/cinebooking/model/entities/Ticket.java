package com.tiendq.cinebooking.model.entities;

import com.tiendq.cinebooking.model.enums.EStatusTicket;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Seat seat;

    @OneToOne
    @NotNull
    private Room room;

    @ManyToOne
    private User user;

    @OneToOne
    @NotNull
    private Film film;

    @Enumerated(EnumType.STRING)
    private EStatusTicket status;

    private Double price;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "showtime_id")
    private Showtime showtime;
}
