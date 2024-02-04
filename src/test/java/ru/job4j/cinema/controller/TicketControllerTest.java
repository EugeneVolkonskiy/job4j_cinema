package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private FilmSessionService filmSessionService;
    private HallService hallService;
    private TicketService ticketService;
    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        hallService = mock(HallService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(filmSessionService, hallService, ticketService);
    }

    @Test
    public void whenRequestOrderInfoPageThenGetOrderInfoPage() {
        var filmSessionDto = new FilmSessionDto(1, 1, 2, LocalDateTime.now(), LocalDateTime.now(), 350, "test1");
        var hall = new Hall(filmSessionDto.getHallId(), "test", 5, 7, "test");
        var rows = hallService.getRowNumbers(hall.getRowCount());
        var places = hallService.getPlaceNumbers(hall.getPlaceCount());
        when(filmSessionService.findById(filmSessionDto.getId())).thenReturn(filmSessionDto);
        when(hallService.findById(filmSessionService.getHallId(filmSessionDto.getId()))).thenReturn(hall);
        when(hallService.getRowNumbers(hall.getRowCount())).thenReturn(rows);
        when(hallService.getPlaceNumbers(hall.getPlaceCount())).thenReturn(places);

        var model = new ConcurrentModel();
        var view = ticketController.getOrderInfo(model, filmSessionDto.getId());
        var expectedFilmSession = model.getAttribute("filmSessionDto");
        var expectedHall = model.getAttribute("hall");
        var expectedRows = model.getAttribute("rows");
        var expectedPlaces = model.getAttribute("places");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(expectedFilmSession).isEqualTo(filmSessionDto);
        assertThat(expectedHall).isEqualTo(hall);
        assertThat(expectedRows).isEqualTo(rows);
        assertThat(expectedPlaces).isEqualTo(places);
    }

    @Test
    public void whenSaveTicketThanGetFailMessage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.empty());
        var expectedMessage = "Не удалось приобрести билет на заданное место. Вероятно оно уже занято. "
                + "Выберите, пожалуйста, другое место.";

        var model = new ConcurrentModel();
        var view = ticketController.saveTicket(model, ticket, 1);
        var actualMessage = model.getAttribute("fail");

        assertThat(view).isEqualTo("errors/409");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenSaveTicketThanGetSuccessMessage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        var user = new User(1, "Ivan", "test@mail.ru", "test");
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        String expectedMessage = String.format("Вы успешно приобрели билет на %s ряд, %s место.", ticket.getRowNumber(), ticket.getPlaceNumber());

        var model = new ConcurrentModel();
        var view = ticketController.saveTicket(model, ticket, 1);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

