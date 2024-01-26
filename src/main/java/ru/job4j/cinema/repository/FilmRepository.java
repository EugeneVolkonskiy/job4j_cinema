package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.FilmDto;

import java.util.Collection;

public interface FilmRepository {

    Collection<FilmDto> findAll();

    FilmDto findById(int id);
}
