package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.ShowtimeDTO;
import com.tiendq.cinebooking.service.ShowtimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @GetMapping("/films/{filmId}/showtimes")
    public ResponseEntity<List<ShowtimeDTO>> getShowtimeOfFilm(@PathVariable Long filmId) {
        return ResponseEntity.ok(showtimeService.getAllShowtimeByFilmId(filmId));
    }

    @GetMapping("/films-showing")
    public ResponseEntity<List<ShowtimeDTO>> getAllFilmShowByDay(@RequestParam Instant time) {
        return ResponseEntity.ok(showtimeService.getAllShowtimeByDay(time));
    }

    @GetMapping("/showtimes")
    public ResponseEntity<List<ShowtimeDTO>> getAllShowtime() {
        return ResponseEntity.ok(showtimeService.getAllShowtime());
    }

    @PostMapping("/management/showtimes")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertShowtime(@RequestBody @Valid ShowtimeDTO showtimeDTO) {
        showtimeService.insertShowtime(showtimeDTO);
    }

    @PatchMapping("/management/showtimes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateShowtime(@PathVariable Long id,
                               @RequestBody @Valid ShowtimeDTO showtimeDTO) {
        log.warn("[CONTROLLER] - UPDATE SHOWTIME: ID - "+id+", SHOWTIME_DTO - "+showtimeDTO);
        showtimeService.updateShowtime(id, showtimeDTO);
    }

    @DeleteMapping("/management/showtimes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
    }
}
