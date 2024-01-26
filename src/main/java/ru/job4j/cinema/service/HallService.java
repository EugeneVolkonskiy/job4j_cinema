package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;

import java.util.List;

public interface HallService {

    Hall findById(int id);

    List<Integer> getRowNumbers(int id);

    List<Integer> getPlaceNumbers(int id);
}
