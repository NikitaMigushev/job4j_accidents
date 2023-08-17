package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.AccidentType;

public interface SpringDataAccidentTypeRepository extends JpaRepository<AccidentType, Integer> {

}
