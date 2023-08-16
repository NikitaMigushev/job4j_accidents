package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

@Repository
public interface SpringDataAccidentRepository extends JpaRepository<Accident, Integer> {
    Collection<Accident> findByType(AccidentType accidentType);
}
