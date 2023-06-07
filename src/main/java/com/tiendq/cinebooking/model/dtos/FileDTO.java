package com.tiendq.cinebooking.model.dtos;

import com.tiendq.cinebooking.model.enums.EFileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private Long id;

    private String name;

    private String path;

    private byte[] data;
}
