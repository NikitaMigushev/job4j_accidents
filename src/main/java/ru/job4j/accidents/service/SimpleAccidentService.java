package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.HibernateAccidentTypeRepository;
import ru.job4j.accidents.repository.SpringDataAccidentRepository;

import java.util.Collection;
import java.util.Optional;


@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final SpringDataAccidentRepository accidentRepository;
    private final HibernateAccidentTypeRepository accidentTypeService;
    private final RuleService ruleService;

    @Override
    public Optional<Accident> save(Accident accident) {
        return Optional.ofNullable(accidentRepository.save(accident));
    }

    @Override
    public Optional<Accident> save(Accident accident, String[] ruleIds) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()).get());
        accident.setRules(ruleService.findByIds(ruleIds));
        return Optional.ofNullable(accidentRepository.save(accident));
    }

    @Override
    public boolean deleteById(int id) {
        accidentRepository.deleteById(id);
        return !accidentRepository.existsById(id); // To verify deletion
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
        return accidentRepository.findByType(accidentType);
    }

    @Override
    public boolean update(Accident updatedAccident) {
        Accident existingAccident = accidentRepository.findById(updatedAccident.getId())
                .orElse(null);

        if (existingAccident != null) {
            existingAccident.setDescription(updatedAccident.getDescription());
            existingAccident.setName(updatedAccident.getName());
            // ... Update other fields as needed
            accidentRepository.save(existingAccident);
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Accident updatedAccident, String[] ruleIds) {
        if (update(updatedAccident)) {
            // Update rules here
            // ...
            return true;
        }
        return false;
    }


}
