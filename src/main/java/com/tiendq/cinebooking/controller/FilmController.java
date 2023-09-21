package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.FilmDTO;
import com.tiendq.cinebooking.model.entities.Film;
import com.tiendq.cinebooking.service.FilmService;
import com.tiendq.cinebooking.utils.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/films/search")
    public ResponseEntity<Page<FilmDTO>> search(@RequestParam(required = false) String key,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "30") int size,
                                                @RequestParam(defaultValue = "name") String[] properties,
                                                @RequestParam(defaultValue = "ASC") Sort.Direction sort) {
        log.warn("[CONTROLLER] - SEARCH REQUEST: key = {}, page = {}, size = {}, properties = {}, sort = {}",
                key, page, size, properties, sort);
        return ResponseEntity.ok(filmService.search(key, page, size, sort, properties));
    }

    @GetMapping("/films/upcoming")
    public ResponseEntity<Set<FilmDTO>> getAllUpComingFilm(@RequestParam(defaultValue = "7") Integer days) {
        return ResponseEntity.ok(filmService.getAllUpComingFilm(days));
    }

    @GetMapping("/films")
    public ResponseEntity<Set<FilmDTO>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<FilmDTO> getById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET FILM BY ID: " + id);
        return ResponseEntity.ok(filmService.getFilmById(id));
    }

    @PostMapping(
            value = "/management/films",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void insertFilm(@RequestParam("files") MultipartFile[] files,
                           @RequestParam("filmDTO") @Valid String filmDTOString) {
        log.warn("[CONTROLLER] - INSERT FILM: " + filmDTOString);
        FilmDTO filmDTO = JsonConverter.convertJsonToObject(filmDTOString, FilmDTO.class);
        filmService.insertFilm(filmDTO, files);
    }


    @PatchMapping("/management/films/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFilm(@PathVariable Long id, @RequestBody FilmDTO filmDTO) {
        log.warn("[CONTROLLER] - UPDATE FILM: " + filmDTO);
        filmService.updateFilm(id, filmDTO);
    }

    @DeleteMapping("/management/films/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE FILM BY ID: " + id);
        filmService.deleteFilm(id);
    }

    @GetMapping("/favorite-movies")
    @ResponseBody
    public List<Film> getProductSalesChartData() {
//        List<Map<String, Object>> chartData = new ArrayList<>();
//        List<Film> films = filmService.favoriteMovie();
//        for (Film f : films) {
//            Map<String, Object> chartRow = new HashMap<>();
//            chartRow.put("name", f.getName());
//            chartRow.put("rate", f.getRate());
//            chartData.add(chartRow);
//        }
        return filmService.favoriteMovie();
    }
}
