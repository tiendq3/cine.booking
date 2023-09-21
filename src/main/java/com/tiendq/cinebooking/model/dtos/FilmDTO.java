package com.tiendq.cinebooking.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiendq.cinebooking.model.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {

    private Long id;

    @NotEmpty(message = "The name of the movie can't be empty")
    private String name;

    private String director;

    private String actors;

    private String description;

    private Double rate;

    @NotNull
    private String time;

    private Set<CategoryDTO> categoryDTOS;

    private Set<FileDTO> fileDTOS;

//    @JsonIgnore
//    private MultipartFile[] files;

    public FilmDTO(Film film) {
        ModelMapper modelMapper = new ModelMapper();
        this.id = film.getId();
        this.name = film.getName();
        this.director = film.getDirector();
        this.actors = film.getActors();
        this.description = film.getDescription();
        this.rate = film.getRate();
        this.time = film.getTime();
        this.categoryDTOS = film
                .getCategories()
                .stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class))
                .collect(Collectors.toSet());
        this.fileDTOS = film
                .getFiles()
                .stream()
                .map(f -> modelMapper.map(f, FileDTO.class))
                .collect(Collectors.toSet());
    }
}
