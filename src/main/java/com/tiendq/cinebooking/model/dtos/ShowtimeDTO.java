package com.tiendq.cinebooking.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiendq.cinebooking.model.entities.Showtime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowtimeDTO {

    private Long id;

    @NotNull(message = "movies of 1 showtime cannot be null")
    private FilmDTO filmDTO;

    @NotNull(message = "room of 1 showtime cannot be null")
    private RoomDTO roomDTO;

    //    @NotNull(message = "price of 1 showtime cannot be null")
//    @JsonIgnore
    private Double price;

    @NotNull(message = "start-time of 1 showtime cannot be null")
    private Instant startTime;

    @NotNull(message = "end-time of 1 showtime cannot be null")
    private Instant endTime;

    public ShowtimeDTO(Showtime showtime) {
        ModelMapper modelMapper = new ModelMapper();
        this.id = showtime.getId();
        this.filmDTO = modelMapper.map(showtime.getFilm(), FilmDTO.class);
        this.roomDTO = modelMapper.map(showtime.getRoom(), RoomDTO.class);
        this.price = showtime.getPrice();
        this.startTime = showtime.getStartTime();
        this.endTime = showtime.getEndTime();
    }
}
