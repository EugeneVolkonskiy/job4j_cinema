package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;

@Service
public class SimpleFilmService implements FilmService {

    private FilmRepository filmRepository;

    public SimpleFilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public FilmDto findById(int id) {
        return filmRepository.findById(id);
    }
}
