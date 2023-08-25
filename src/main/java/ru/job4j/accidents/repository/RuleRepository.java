package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RuleRepository {
    Optional<Rule> save(Rule rule);
    boolean update(Rule updatedRule);
    boolean deleteById(int id);
    Optional<Rule> findById(int id);
    Collection<Rule> findAll();
    Set<Rule> findByIds(String[] ids);
}
