package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;

@Repository
public interface SpringDataAccidentRuleRepository extends JpaRepository<Rule, Integer> {
    Set<Rule> findByIdIn(Collection<Integer> ids);
}
