package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RuleService {
    Optional<Rule> save(Rule rule);
    boolean update(Rule rule);
    boolean deleteById(int id);
    Optional<Rule> findById(int id);
    Collection<Rule> findAll();
    Collection<Rule> findByIds(String[] ids);
    Set<Rule> getSelectedRules(String[] ids);
}
