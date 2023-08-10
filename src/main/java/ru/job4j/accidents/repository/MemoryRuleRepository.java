package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryRuleRepository implements RuleRepository {
    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

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
    public Collection<Rule> findByIds(String[] ids) {
        List<Rule> foundRules = new ArrayList<>();
        for (String idStr : ids) {
            try {
                int id = Integer.parseInt(idStr);
                Rule rule = rules.get(id);
                if (rule != null) {
                    foundRules.add(rule);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + idStr);
            }
        }
        return foundRules;
    }
}
