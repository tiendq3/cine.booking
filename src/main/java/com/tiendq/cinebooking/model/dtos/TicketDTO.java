package com.tiendq.cinebooking.model.dtos;

import com.tiendq.cinebooking.model.entities.Ticket;
import com.tiendq.cinebooking.model.enums.EStatusTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Long id;

    @NotNull
    private SeatDTO seatDTO;

    private String roomName;

    @Email
    private String emailUser;

    @NotNull
    private FilmDTO filmDTO;

    private EStatusTicket status;

    @NotNull
    private Double price;

    @NotNull
    private ShowtimeDTO showtimeDTO;

    public TicketDTO(Ticket ticket) {
        ModelMapper modelMapper = new ModelMapper();
        this.id = ticket.getId();
        if (ticket.getUser() == null) this.emailUser = null;
        else this.emailUser = ticket.getUser().getEmail();
        this.seatDTO = modelMapper.map(ticket.getSeat(), SeatDTO.class);
        this.roomName = ticket.getRoom().getName();
        this.filmDTO = modelMapper.map(ticket.getFilm(), FilmDTO.class);
        this.showtimeDTO = new ShowtimeDTO(ticket.getShowtime());
        this.status = ticket.getStatus();
        this.price = ticket.getPrice();
    }
}
