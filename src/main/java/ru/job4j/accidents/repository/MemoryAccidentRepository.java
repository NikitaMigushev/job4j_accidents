package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class MemoryAccidentRepository implements AccidentRepository {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Optional<Accident> save(Accident accident) {
        int id = idCounter.incrementAndGet();
        accident.setId(id);
        accidents.put(accident.getId(), accident);
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        int id = accident.getId();
        if (accidents.containsKey(id)) {
            accidents.put(id, accident);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Collection<Accident> findByAccidentType(AccidentType accidentType) {
        return accidents.values()
                .stream()
                .filter(accident -> accident.getType().equals(accidentType))
                .collect(Collectors.toList());
    }
}
