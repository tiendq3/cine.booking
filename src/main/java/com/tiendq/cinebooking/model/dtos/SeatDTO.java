package com.tiendq.cinebooking.model.dtos;

import com.tiendq.cinebooking.model.enums.EStatusSeat;
import com.tiendq.cinebooking.model.enums.ETypeSeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {

    private Long id;

    private String name;

    private EStatusSeat status;

    private ETypeSeat type;

    private Double price;

    private RoomDTO roomDTO;
}
