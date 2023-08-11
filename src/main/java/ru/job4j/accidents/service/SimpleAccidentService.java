package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.MemoryAccidentRepository;

import java.util.Collection;
import java.util.Optional;


@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final MemoryAccidentRepository accidentRepository;
    private final AccidentTypeService accidentTypeService;

    @Override
    public Optional<Accident> save(Accident accident) {
        var saveAccident = accident;
        if (accident.getType().getId() > 0 && accident.getType().getName() == null) {
            var selectedAccidentType = accidentTypeService.findById(accident.getType().getId()).get();
            saveAccident.setType(selectedAccidentType);
        }
        return accidentRepository.save(saveAccident);
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
