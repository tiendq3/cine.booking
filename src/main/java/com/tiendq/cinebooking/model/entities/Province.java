package com.tiendq.cinebooking.model.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Province implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the name of province isn't empty")
    private String name;

    private String type;

    @ManyToMany
    private Set<District> districts;
}
