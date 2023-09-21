package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.BadRequestException;
import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.ShowtimeDTO;
import com.tiendq.cinebooking.model.entities.Film;
import com.tiendq.cinebooking.model.entities.Room;
import com.tiendq.cinebooking.model.entities.Showtime;
import com.tiendq.cinebooking.model.entities.Ticket;
import com.tiendq.cinebooking.model.enums.EStatusTicket;
import com.tiendq.cinebooking.repository.BookingRepository;
import com.tiendq.cinebooking.repository.FilmRepository;
import com.tiendq.cinebooking.repository.RoomRepository;
import com.tiendq.cinebooking.repository.ShowtimeRepository;
import com.tiendq.cinebooking.service.ShowtimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final FilmRepository filmRepository;

    private final ModelMapper modelMapper;

    @Override
    public ShowtimeDTO getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime == null) throw new NotFoundException("not found showtime by id: " + id);
        return new ShowtimeDTO(showtime);
    }

    @Override
    public List<ShowtimeDTO> getAllShowtime() {
        List<Showtime> showtimes = showtimeRepository.findAll(Sort.by(Sort.Direction.ASC, "startTime"));
        return showtimes
                .stream()
                .map(ShowtimeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeDTO> getAllShowtimeByFilmId(Long filmId) {
        return showtimeRepository
                .findShowtimesByFilmIdAndStartTimeGreaterThan(filmId, Instant.now().plusSeconds(1800))
                .stream()
                .map(ShowtimeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeDTO> getAllShowtimeByDay(Instant time) {
        if (time.getEpochSecond() >= Instant.now().getEpochSecond()) {
            List<Showtime> showtimes = showtimeRepository.getAllShowtimeByTime(time.plus(Duration.ofDays(1)));
            return showtimes
                    .stream()
                    .map(ShowtimeDTO::new)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    @Override
    public void insertShowtime(ShowtimeDTO showtimeDTO) {
        Showtime showtime = validateShowtime(showtimeDTO);
        // khi admin tạo ra 1 showtime --> đi kèm với đó việc tạo ra các vé cho showtime
        showtimeRepository.save(showtime);
        Set<Ticket> tickets = showtime
                .getRoom()
                .getSeats()
                .stream()
//                .filter(s -> s.getStatus().equals(EStatusSeat.ACTIVE))
                .map(s -> Ticket.
                        builder()
                        .status(EStatusTicket.UN_BOOKED)
                        .seat(s)
                        .film(showtime.getFilm())
                        .showtime(showtime)
                        .room(showtime.getRoom())
                        .build())
                .collect(Collectors.toSet());
        bookingRepository.saveAll(tickets);
    }

    @Override
    public void updateShowtime(Long id, ShowtimeDTO showtimeDTO) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime == null) throw new NotFoundException("not found showtime by id: " + id);
        deleteShowtime(showtime.getId());

        Showtime showtimeValidation = validateShowtime(showtimeDTO);
        showtimeRepository.save(showtimeValidation);

        Set<Ticket> newTickets = showtimeValidation
                .getRoom()
                .getSeats()
                .stream()
//                .filter(s -> s.getStatus().equals(EStatusSeat.ACTIVE))
                .map(s -> Ticket.
                        builder()
                        .status(EStatusTicket.UN_BOOKED)
                        .seat(s)
                        .film(showtimeValidation.getFilm())
                        .showtime(showtimeValidation)
                        .room(showtimeValidation.getRoom())
                        .build())
                .collect(Collectors.toSet());
        bookingRepository.saveAll(newTickets);
    }

    @Override
    public void deleteShowtime(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime == null) throw new NotFoundException("not found showtime by id: " + id);
        List<Ticket> tickets = bookingRepository.findTicketByShowtimeId(id, Sort.by(Sort.Direction.ASC, "seat"));
        bookingRepository.deleteAll(tickets);
        showtimeRepository.delete(showtime);
    }

    Showtime validateShowtime(ShowtimeDTO showtimeDTO) {
        if (showtimeDTO.getStartTime().getEpochSecond() < Instant.now().getEpochSecond() ||
                showtimeDTO.getStartTime().getEpochSecond() >= showtimeDTO.getEndTime().getEpochSecond())
            throw new BadRequestException("Start time cannot be before current or end time must be after start time");
        // check trùng room, trùng time
        Film film = filmRepository.findFilmByName(showtimeDTO.getFilmDTO().getName()).orElse(null);
        if (film == null) throw new NotFoundException("not exist this film");

        Room room = roomRepository.findRoomByName(showtimeDTO.getRoomDTO().getName()).orElse(null);
        if (room == null) throw new NotFoundException("not exist room: " + showtimeDTO.getRoomDTO().getName());

        List<Showtime> showtimes = showtimeRepository.findShowtimeByRoom(room);
        for (Showtime showtime : showtimes) {
            if ((showtimeDTO.getStartTime().getEpochSecond() <= showtime.getEndTime().getEpochSecond()
                    && showtimeDTO.getStartTime().getEpochSecond() >= showtime.getStartTime().getEpochSecond())
                    || (showtimeDTO.getEndTime().getEpochSecond() <= showtime.getEndTime().getEpochSecond()
                    && showtimeDTO.getEndTime().getEpochSecond() >= showtime.getStartTime().getEpochSecond()))
                throw new BadRequestException(room.getName() + " has showtimes from " + showtime.getStartTime() + " to " + showtime.getEndTime());
        }
        Showtime showtime = modelMapper.map(showtimeDTO, Showtime.class);
        showtime.setRoom(room);
        showtime.setFilm(film);
        showtime.setPrice(showtimeDTO.getPrice());
        return showtime;
    }
}
