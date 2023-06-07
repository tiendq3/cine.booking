package com.tiendq.cinebooking.model.entities;

import com.tiendq.cinebooking.model.enums.EStatusRoom;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    private String description;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private EStatusRoom status;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats;

    @ManyToOne
    private Cinema cinema;
}
