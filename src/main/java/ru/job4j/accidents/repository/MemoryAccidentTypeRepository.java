package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {
    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public MemoryAccidentTypeRepository(Map<Integer, AccidentType> accidentTypes) {
        this.accidentTypes = accidentTypes;
        this.save(new AccidentType(1, "Две машины"));
        this.save(new AccidentType(2, "Машина и человек"));
        this.save(new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Optional<AccidentType> save(AccidentType accidentType) {
        int id = idCounter.incrementAndGet();
        accidentType.setId(id);
        accidentTypes.put(accidentType.getId(), accidentType);
        return Optional.of(accidentType);
    }

    @Override
    public boolean update(AccidentType updatedAccidentType) {
        return accidentTypes.computeIfPresent(updatedAccidentType.getId(), (key, existingType) -> {
            existingType.setName(updatedAccidentType.getName());
            return existingType;
        }) != null;
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
