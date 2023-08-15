package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JdbcAccidentRuleRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {
    private final JdbcAccidentRuleRepository ruleRepository;

    @Override
    public Optional<Rule> save(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public boolean update(Rule updatedRule) {
        return ruleRepository.update(updatedRule);
    }

    @Override
    public boolean deleteById(int id) {
        return ruleRepository.deleteById(id);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Set<Rule> findByIds(String[] ids) {
        return ruleRepository.findByIds(ids);
    }
}
