package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;


@Service
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;

    public SimpleAccidentService(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident updatedAccident) {
        return accidentRepository.update(updatedAccident);
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
    public Collection<Accident> findByAccidentType(AccidentType accidentType) {
        return accidentRepository.findByAccidentType(accidentType);
    }
}
