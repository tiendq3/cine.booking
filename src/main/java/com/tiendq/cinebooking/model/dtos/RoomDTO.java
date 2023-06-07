package com.tiendq.cinebooking.model.dtos;

import com.tiendq.cinebooking.model.enums.EStatusRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private Long id;

    private String name;

    private String description;

    private Integer capacity;

    private EStatusRoom status;

    private Set<SeatDTO> seatDTOS;
}
