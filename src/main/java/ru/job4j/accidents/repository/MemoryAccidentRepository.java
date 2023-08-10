package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class MemoryAccidentRepository implements AccidentRepository {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public MemoryAccidentRepository(Map<Integer, Accident> accidents) {
        this.accidents = accidents;
        this.save(new Accident("Test name", "Test text", "test address"));
        this.save(new Accident("Test name", "Test text", "test address"));
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        int id = idCounter.incrementAndGet();
        accident.setId(id);
        accidents.put(accident.getId(), accident);
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident updatedAccident) {
        return accidents.computeIfPresent(updatedAccident.getId(), (key, existingAccident) -> {
            existingAccident.setName(updatedAccident.getName());
            existingAccident.setText(updatedAccident.getText());
            existingAccident.setAddress(updatedAccident.getAddress());
            existingAccident.setType(updatedAccident.getType());
            existingAccident.setRules(updatedAccident.getRules());
            return existingAccident;
        }) != null;
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
