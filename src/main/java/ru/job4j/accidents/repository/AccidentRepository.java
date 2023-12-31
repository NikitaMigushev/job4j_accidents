package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AccidentRepository {
    Optional<Accident> save(Accident accident);
    boolean update(Accident updatedAccident);
    boolean deleteById(int id);
    Optional<Accident> findById(int id);
    Collection<Accident> findAll();
    Collection<Accident> findByAccidentType(AccidentType accidentType);
}
