package com.tiendq.cinebooking.model.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "districts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class District implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the name of district isn't empty")
    private String name;

    private String type;
}
