package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexControllerTest {

    @Test
    public void whenRequestIndexPageThenGetIndexPage() {
        IndexController indexController = new IndexController();
        assertThat(indexController.getIndex()).isEqualTo("index");
    }
}