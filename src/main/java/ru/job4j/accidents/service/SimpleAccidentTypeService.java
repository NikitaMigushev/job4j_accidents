package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.SpringDataAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {
    @Autowired
    private SpringDataAccidentTypeRepository accidentTypeRepository;

    @Override
    public Optional<AccidentType> save(AccidentType accidentType) {
        return Optional.ofNullable(accidentTypeRepository.save(accidentType));
    }

    @Override
    public boolean update(AccidentType accidentType) {
        if (accidentType.getId() == 0) {
            return false;
        }

        Optional<AccidentType> existingTypeOptional = accidentTypeRepository.findById(accidentType.getId());
        if (existingTypeOptional.isPresent()) {
            AccidentType existingType = existingTypeOptional.get();
            existingType.setName(accidentType.getName());
            accidentTypeRepository.save(existingType);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        accidentTypeRepository.deleteById(id);
        return !accidentTypeRepository.existsById(id);
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
