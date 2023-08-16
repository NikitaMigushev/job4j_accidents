package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAccidentTypeRepository implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<AccidentType> save(AccidentType accidentType) {
        try {
            crudRepository.run(session -> session.save(accidentType));
            return Optional.of(accidentType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(AccidentType updatedAccidentType) {
        try {
            crudRepository.run(session -> session.merge(updatedAccidentType));
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
                    "delete from AccidentType where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT a FROM AccidentType a WHERE a.id = :fId",
                    AccidentType.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<AccidentType> findAll() {
        try {
            return crudRepository.query(
                    "SELECT a FROM AccidentType a",
                    AccidentType.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
