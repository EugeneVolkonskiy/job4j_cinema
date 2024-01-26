package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {

    private FilmSessionController filmSessionController;
    private FilmSessionService filmSessionService;

    @BeforeEach
    public void initService() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestFilmSessionListPageThenGetPageWithFilmSessions() {
        var filmSessionDto1 = new FilmSessionDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(), 350, "test1");
        var filmSessionDto2 = new FilmSessionDto(2, 2, 2, LocalDateTime.now(), LocalDateTime.now(), 350, "test2");
        var expectedFilmSessions = List.of(filmSessionDto1, filmSessionDto2);
        when(filmSessionService.findAll()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("filmSessionDto");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }
}