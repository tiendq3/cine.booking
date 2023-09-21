package com.tiendq.cinebooking.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private Long id;

    @NotEmpty(message = "tên phòng không được để trống")
    private String name;

    private String description;

    private Integer capacity;

    private String status;

    private Set<SeatDTO> seatDTOS;
}
