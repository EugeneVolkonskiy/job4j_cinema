package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.FilmDto;

import java.util.Collection;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<FilmDto> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT f.id, f.name as \"film_name\" FROM films f");
            return query.setColumnMappings(FilmDto.COLUMN_MAPPING).executeAndFetch(FilmDto.class);
        }
    }

    @Override
    public FilmDto findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    """
                            SELECT f.id, f.name as "film_name", f.description, f."year", f.minimal_age,
                            f.duration_in_minutes, f.file_id, g.name as "genre"
                            FROM films as f
                            JOIN genres as g
                            ON f.genre_id = g.id
                            WHERE f.id = :id""");
            query.addParameter("id", id);
            return query.setColumnMappings(FilmDto.COLUMN_MAPPING).executeAndFetchFirst(FilmDto.class);
        }
    }
}
