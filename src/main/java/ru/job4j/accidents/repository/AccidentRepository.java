package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentRepository {
    Optional<Accident> save(Accident accident);
    boolean update(Accident accident);
    boolean deleteById(int id);
    Optional<Accident> findById(int id);
    Collection<Accident> findAll();
    int getLastId();
}