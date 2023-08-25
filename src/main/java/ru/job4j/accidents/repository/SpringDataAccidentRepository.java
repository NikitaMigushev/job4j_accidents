package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

public interface SpringDataAccidentRepository extends JpaRepository<Accident, Integer> {
    Collection<Accident> findByType(AccidentType accidentType);
}
