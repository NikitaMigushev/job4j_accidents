package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;


@AllArgsConstructor
@Service
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    @Override
    public boolean deleteById(int id) {
        return accidentRepository.deleteById(id);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public int getLastId() {
        return accidentRepository.getLastId();
    }
}
