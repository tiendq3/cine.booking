package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.ShowtimeDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface ShowtimeService {

    ShowtimeDTO getShowtimeById(Long id);

    List<ShowtimeDTO> getAllShowtime();

    List<ShowtimeDTO> getAllShowtimeByFilmId(Long filmId);

    List<ShowtimeDTO> getAllShowtimeByDay(Instant day);

    void insertShowtime(ShowtimeDTO showtimeDTO);

    void updateShowtime(Long id, ShowtimeDTO showtimeDTO);

    void deleteShowtime(Long id);
}
