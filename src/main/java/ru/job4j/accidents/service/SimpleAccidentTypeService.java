package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeRepository accidentTypeRepository;

    public SimpleAccidentTypeService(AccidentTypeRepository accidentTypeRepository) {
        this.accidentTypeRepository = accidentTypeRepository;
        accidentTypeRepository.save(new AccidentType(1, "Две машины"));
        accidentTypeRepository.save(new AccidentType(2, "Машина и человек"));
        accidentTypeRepository.save(new AccidentType(3, "Машина и велосипед"));
    }

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
