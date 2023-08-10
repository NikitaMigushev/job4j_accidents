package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeService {
    Optional<AccidentType> save(AccidentType accidentType);
    boolean update(AccidentType updatedAccidentType);
    boolean deleteById(int id);
    Optional<AccidentType> findById(int id);
    Collection<AccidentType> findAll();
}
