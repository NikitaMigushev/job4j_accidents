package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.HibernateAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {
    private final HibernateAccidentTypeRepository accidentTypeRepository;

    @Override
    public Optional<AccidentType> save(AccidentType accidentType) {
        return accidentTypeRepository.save(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType) {
        return accidentTypeRepository.update(accidentType);
    }

    @Override
    public boolean deleteById(int id) {
        return accidentTypeRepository.deleteById(id);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }
}
