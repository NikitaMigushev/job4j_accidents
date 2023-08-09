package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SimpleRuleService implements RuleService {
    private final RuleRepository ruleRepository;

    public SimpleRuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
        ruleRepository.save(new Rule(2, "Статья. 2"));
        ruleRepository.save(new Rule(3, "Статья. 3"));
    }

    @Override
    public Optional<Rule> save(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public boolean update(Rule rule) {
        return ruleRepository.update(rule);
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
    public Collection<Rule> findByIds(String[] ids) {
        return ruleRepository.findByIds(ids);
    }

    @Override
    public Set<Rule> getSelectedRules(String[] ids) {
        Set<Rule> selectedRules = new HashSet<>();
        selectedRules.addAll(ruleRepository.findByIds(ids));
        return selectedRules;
    }
}
