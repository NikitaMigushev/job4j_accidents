package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.HibernateAccidentRepository;
import ru.job4j.accidents.repository.HibernateAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;


@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final HibernateAccidentRepository accidentRepository;
    private final HibernateAccidentTypeRepository accidentTypeService;
    private final RuleService ruleService;

    @Override
    public Optional<Accident> save(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public Optional<Accident> save(Accident accident, String[] ruleIds) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()).get());
        accident.setRules(ruleService.findByIds(ruleIds));
        return accidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident updatedAccident) {
        return accidentRepository.update(updatedAccident);
    }

    public boolean update(Accident updatedAccident, String[] ruleIds) {
        updatedAccident.setType(accidentTypeService.findById(updatedAccident.getType().getId()).get());
        updatedAccident.setRules(ruleService.findByIds(ruleIds));
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
