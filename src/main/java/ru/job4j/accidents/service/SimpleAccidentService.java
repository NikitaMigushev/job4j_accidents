package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.SpringDataAccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    @Autowired
    private SpringDataAccidentRepository accidentRepository;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @Override
    public Optional<Accident> save(Accident accident) {
        return Optional.ofNullable(accidentRepository.save(accident));
    }

    @Override
    public Optional<Accident> save(Accident accident, String[] ruleIds) {
        setTypeAndRuleInAccident(accident, ruleIds);
        return Optional.ofNullable(accidentRepository.save(accident));
    }

    @Override
    public boolean deleteById(int id) {
        accidentRepository.deleteById(id);
        return !accidentRepository.existsById(id);
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
            existingAccident.setType(updatedAccident.getType());
            existingAccident.setRules(updatedAccident.getRules());
            accidentRepository.save(existingAccident);
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Accident updatedAccident, String[] ruleIds) {
        setTypeAndRuleInAccident(updatedAccident, ruleIds);
        if (update(updatedAccident)) {
            return true;
        }
        return false;
    }

    private void setTypeAndRuleInAccident(Accident accident, String[] ruleIds) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()).get());
        accident.setRules(ruleService.findByIds(ruleIds));
    }
}
