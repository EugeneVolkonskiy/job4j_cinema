package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private FilmSessionService filmSessionService;
    private HallService hallService;
    private TicketService ticketService;

    public TicketController(FilmSessionService filmSessionService, HallService hallService, TicketService ticketService) {
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
        this.ticketService = ticketService;
    }

    @GetMapping("/buy/{id}")
    public String getOrderInfo(Model model, @PathVariable int id) {
        model.addAttribute("filmSessionDto", filmSessionService.findById(id));
        model.addAttribute("hall", hallService.findById(filmSessionService.getHallId(id)));
        model.addAttribute("rows", hallService.getRowNumbers(filmSessionService.getHallId(id)));
        model.addAttribute("places", hallService.getPlaceNumbers(filmSessionService.getHallId(id)));
        return "tickets/buy";
    }

    @PostMapping("/buy/{id}")
    public String saveTicket(Model model, @ModelAttribute Ticket ticket, @PathVariable int id) {
        getOrderInfo(model, id);
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("fail", "Не удалось приобрести билет на заданное место. "
                    + "Вероятно оно уже занято. Выберите, пожалуйста, другое место.");
            return "errors/409";
        }
        String message = String.format("Вы успешно приобрели билет на %s ряд, %s место.", ticket.getRowNumber(), ticket.getPlaceNumber());
        model.addAttribute("message", message);
        return "tickets/buy";
    }
}
