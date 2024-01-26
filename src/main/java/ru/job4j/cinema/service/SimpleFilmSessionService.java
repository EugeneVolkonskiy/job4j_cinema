package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.FilmSessionRepository;

import java.util.Collection;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository) {
        this.filmSessionRepository = filmSessionRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll();
    }

    @Override
    public FilmSessionDto findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public int getHallId(int id) {
        return filmSessionRepository.findById(id).getHallId();
    }
}
