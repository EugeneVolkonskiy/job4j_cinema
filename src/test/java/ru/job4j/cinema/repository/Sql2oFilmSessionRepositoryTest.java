package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    public void whenFindAllThen10() {
        var sessions = sql2oFilmSessionRepository.findAll().size();
        assertThat(sessions).isEqualTo(10);
    }

    @Test
    public void whenFindAllThenFindFirstPrice350() {
        var result = sql2oFilmSessionRepository.findAll().stream().findFirst().get().getPrice();
        assertThat(result).isEqualTo(350);
    }

    @Test
    public void whenFindById3ThenFilmNameHolop2() {
        var result = sql2oFilmSessionRepository.findById(2).getFilmName();
        assertThat(result).isEqualTo("Холоп 2");
    }
}