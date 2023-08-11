package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class MemoryRuleRepository implements RuleRepository {
    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public MemoryRuleRepository(Map<Integer, Rule> rules) {
        this.rules = rules;
        this.save(new Rule(2, "Статья. 2"));
        this.save(new Rule(3, "Статья. 3"));
    }

    @Override
    public Optional<Rule> save(Rule rule) {
        int id = idCounter.incrementAndGet();
        rule.setId(id);
        rules.put(rule.getId(), rule);
        return Optional.of(rule);
    }

    @Override
    public boolean update(Rule updatedRule) {
        return rules.computeIfPresent(updatedRule.getId(), (key, existingRule) -> {
            existingRule.setName(updatedRule.getName());
            return existingRule;
        }) != null;
    }

    @Override
    public boolean deleteById(int id) {
        return rules.remove(id) != null;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }

    @Override
    public Set<Rule> findByIds(String[] ids) {
        return Stream.of(ids)
                .map(idStr -> {
                        int id = Integer.parseInt(idStr);
                        return rules.get(id);
                }).filter(rule -> rule != null)
                .collect(Collectors.toSet());
    }
}
