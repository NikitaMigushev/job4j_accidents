package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Service
public interface AccidentService {
    Optional<Accident> save(Accident accident);
    boolean update(Accident updatedAccident);
    boolean deleteById(int id);
    Optional<Accident> findById(int id);
    Collection<Accident> findAll();
    Collection<Accident> findByAccidentType(AccidentType accidentType);
    Optional<Accident> save(Accident accident, String[] ruleIds);
    boolean update(Accident updatedAccident, String[] ruleIds);
}
