package com.tiendq.cinebooking.model.entities;

import com.tiendq.cinebooking.model.enums.EFileType;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    @Enumerated(EnumType.STRING)
    private EFileType type;

    private String ext;

    private Double size;

    private byte[] data;

}
