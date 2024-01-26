package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {

    private FilmController filmController;
    private FilmService filmService;

    @BeforeEach
    public void initService() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenRequestFilmsListPageThenGetPageWithFilms() {
        var filmDto1 = new FilmDto(1, "test1", "description1", 2024, 1, 18, 100, 1, "test1");
        var filmDto2 = new FilmDto(2, "test2", "description2", 2024, 2, 18, 100, 2, "test2");
        var expectedFilms = List.of(filmDto1, filmDto2);
        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }

    @Test
    public void whenRequestFilmsInfoPageThenGetPageWithInfo() {
        var filmDto = new FilmDto(1, "test1", "description1", 2024, 1, 18, 100, 1, "test1");
        when(filmService.findById(filmDto.getId())).thenReturn(filmDto);

        var model = new ConcurrentModel();
        var view = filmController.getInfoById(model, filmDto.getId());
        var actualFilm = model.getAttribute("filmDto");

        assertThat(view).isEqualTo("films/info");
        assertThat(actualFilm).isEqualTo(filmDto);
    }
}