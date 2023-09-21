package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.FilmDTO;
import com.tiendq.cinebooking.model.entities.File;
import com.tiendq.cinebooking.model.entities.Film;
import com.tiendq.cinebooking.model.entities.Showtime;
import com.tiendq.cinebooking.repository.FileRepository;
import com.tiendq.cinebooking.repository.FilmRepository;
import com.tiendq.cinebooking.repository.ShowtimeRepository;
import com.tiendq.cinebooking.service.FileService;
import com.tiendq.cinebooking.service.FilmService;
import com.tiendq.cinebooking.service.ShowtimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final ShowtimeService showtimeService;

    private final FileRepository fileRepository;

    private final FileService fileService;

    private final ShowtimeRepository showtimeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<FilmDTO> search(String key, int page, int size, Sort.Direction sort, String... properties) {
        log.warn("[SERVICE] - SEARCH");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, properties);
        if (sort.isDescending()) {
            pageable = PageRequest.of(page, size, Sort.Direction.DESC, properties);
        }
        Page<Film> products = filmRepository.searchBy(key, pageable);
        return products.map(film -> modelMapper.map(film, FilmDTO.class));
    }

    @Override
    public Set<FilmDTO> getAllUpComingFilm(Integer days) {
        List<Showtime> showtimes = showtimeRepository.getAllShowtimeByTime(Instant.now().plusSeconds(days * 86400));
        return showtimes
                .stream()
                .map(Showtime::getFilm)
                .map(FilmDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FilmDTO> getAllFilms() {
        List<Film> films = filmRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return films
                .stream()
                .map(FilmDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public FilmDTO getFilmById(Long id) {
        log.warn("[SERVICE] - GET FILM BY ID");
        Film film = filmRepository.findById(id).orElse(null);
        if (film == null) throw new NotFoundException("not found film by id: " + id);
        return new FilmDTO(film);
    }

    @Override
    public void insertFilm(FilmDTO filmDTO, MultipartFile[] files) {
        log.warn("[SERVICE] - INSERT FILM");
        List<File> filesDB = fileService.uploadFile(files);
        Film film = modelMapper.map(filmDTO, Film.class);
        film.setFiles(filesDB);
        filmRepository.save(film);
    }

    @Override
    public void updateFilm(Long id, FilmDTO filmDTO) {
        Film film = filmRepository.findById(id).orElse(null);
        if (film == null) throw new NotFoundException("not found film by id: " + id);
        film.setName(filmDTO.getName());
        film.setActors(filmDTO.getActors());
        film.setDirector(filmDTO.getDirector());
        film.setDescription(filmDTO.getDescription());
        film.setTime(filmDTO.getTime());
        filmRepository.save(film);
    }

    @Override
    public void deleteFilm(Long id) {
        Film film = filmRepository.findById(id).orElse(null);
        if (film == null) throw new NotFoundException("not found film by id: " + id);
        filmRepository.delete(film);
    }

    @Override
    public List<Film> favoriteMovie() {
        return filmRepository.favoriteMovie();
    }
}
