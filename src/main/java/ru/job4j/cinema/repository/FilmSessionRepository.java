package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.Collection;

public interface FilmSessionRepository {

    Collection<FilmSessionDto> findAll();

    FilmSessionDto findById(int id);
}
