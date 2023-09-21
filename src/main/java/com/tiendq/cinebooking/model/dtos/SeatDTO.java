package com.tiendq.cinebooking.model.dtos;

import com.tiendq.cinebooking.model.entities.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {

    private Long id;

    @NotEmpty(message = "tên ghế không được để trống")
    private String name;

    private String status;

    private String type;

    private String roomName;

    public SeatDTO(Seat seat) {
        this.id = seat.getId();
        this.name = seat.getName();
        if (seat.getStatus() != null)
            this.status = seat.getStatus().toString();
        if (seat.getType() != null)
            this.type = seat.getType().toString();
        this.roomName = seat.getRoom().getName();
    }
}
