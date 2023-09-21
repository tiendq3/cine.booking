package com.tiendq.cinebooking.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiendq.cinebooking.model.enums.EStatusSeat;
import com.tiendq.cinebooking.model.enums.ETypeSeat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "tên ghế không được để trống")
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private EStatusSeat status;

    @Enumerated(EnumType.STRING)
    private ETypeSeat type;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    private Room room;
}
