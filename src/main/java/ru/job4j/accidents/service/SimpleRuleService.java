package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.SpringDataAccidentRuleRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {
    @Autowired
    private SpringDataAccidentRuleRepository ruleRepository;

    @Override
    public Optional<Rule> save(Rule rule) {
        return Optional.ofNullable(ruleRepository.save(rule));
    }

    @Override
    public boolean update(Rule updatedRule) {
        if (updatedRule.getId() == 0) {
            return false;
        }
        Optional<Rule> existingRuleOptional = ruleRepository.findById(updatedRule.getId());
        if (existingRuleOptional.isPresent()) {
            Rule existingRule = existingRuleOptional.get();
            existingRule.setName(updatedRule.getName());
            ruleRepository.save(existingRule);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        ruleRepository.deleteById(id);
        return !ruleRepository.existsById(id);
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
        Set<Integer> intIds = Arrays.stream(ids)
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
        return ruleRepository.findByIdIn(intIds);
    }
}
