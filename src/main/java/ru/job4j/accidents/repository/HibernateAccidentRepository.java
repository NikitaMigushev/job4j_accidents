package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAccidentRepository implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Accident> save(Accident accident) {
        try {
            crudRepository.run(session -> session.save(accident));
            return Optional.of(accident);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Accident updatedAccident) {
        try {
            crudRepository.run(session -> session.merge(updatedAccident));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Accident where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Accident> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT a FROM Accident a LEFT JOIN FETCH a.type WHERE a.id = :fId",
                    Accident.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        try {
            return crudRepository.query(
                    "SELECT a FROM Accident a LEFT JOIN FETCH a.type",
                    Accident.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Accident> findByAccidentType(AccidentType accidentType) {
        try {
            return crudRepository.query(
                    "SELECT a FROM Accident a LEFT JOIN FETCH a.type WHERE a.type = :type",
                    Accident.class,
                    Map.of("type", accidentType)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
