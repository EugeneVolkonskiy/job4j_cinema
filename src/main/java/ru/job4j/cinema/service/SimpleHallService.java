package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleHallService implements HallService {

    private HallRepository hallRepository;

    public SimpleHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall findById(int id) {
        return hallRepository.findById(id);
    }

    @Override
    public List<Integer> getRowNumbers(int id) {
        return getNumbers(hallRepository.findById(id).getRowCount());
    }

    @Override
    public List<Integer> getPlaceNumbers(int id) {
        return getNumbers(hallRepository.findById(id).getPlaceCount());
    }

    private List<Integer> getNumbers(int count) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            result.add(i);
        }
        return result;
    }
}
