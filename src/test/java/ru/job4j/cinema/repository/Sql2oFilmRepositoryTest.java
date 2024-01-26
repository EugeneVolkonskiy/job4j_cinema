package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }


    @Test
    public void whenFindAllThenFindFirstNameFerrari() {
        var result = sql2oFilmRepository.findAll().stream().findFirst().get().getFilmName();
        assertThat(result).isEqualTo("Феррари");
    }

    @Test
    public void whenFindAllThenSize5() {
        var result = sql2oFilmRepository.findAll();
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    public void whenFindById1ThenFilmNameFerrari() {
        var result = sql2oFilmRepository.findById(1);
        assertThat(result.getFilmName()).isEqualTo("Феррари");
    }
}