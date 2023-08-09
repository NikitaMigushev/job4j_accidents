package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<AccidentType> save(AccidentType accidentType) {
        int id = idCounter.incrementAndGet();
        accidentType.setId(id);
        accidentTypes.put(accidentType.getId(), accidentType);
        return Optional.of(accidentType);
    }

    @Override
    public boolean update(AccidentType accidentType) {
        int id = accidentType.getId();
        if (accidentTypes.containsKey(id)) {
            accidentTypes.put(id, accidentType);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return accidentTypes.remove(id) != null;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypes.values();
    }
}
