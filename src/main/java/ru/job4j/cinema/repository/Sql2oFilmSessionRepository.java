package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.Collection;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    """
                            SELECT s.id, s.start_time, s.end_time, f.name, s.price
                            FROM film_sessions as s
                            JOIN films as f
                            ON s.film_id = f.id""");
            return query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING).executeAndFetch(FilmSessionDto.class);
        }
    }

    @Override
    public FilmSessionDto findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    """
                            SELECT s.id, s.start_time, s.end_time, s.halls_id, s.price, f.name
                            FROM film_sessions as s
                            JOIN films as f
                            ON s.film_id = f.id
                            WHERE s.id = :id""");
            query.addParameter("id", id);
            return query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING).executeAndFetchFirst(FilmSessionDto.class);
        }
    }
}
