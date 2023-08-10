package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository {
    Optional<Rule> save(Rule rule);
    boolean update(Rule updatedRule);
    boolean deleteById(int id);
    Optional<Rule> findById(int id);
    Collection<Rule> findAll();
    Collection<Rule> findByIds(String[] ids);
}
