package ru.job4j.accidents.repository;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.accidents.model.Rule;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SpringDataAccidentRuleRepositoryTest {
    @Autowired
    private SpringDataAccidentRuleRepository accidentRuleRepository;

    @RepeatedTest(2)
    void saveRetrieve() {
        accidentRuleRepository.save(new Rule("Test1"));
        List<Rule> rules = accidentRuleRepository.findAll();
        assertAll(
                () -> assertEquals(1, rules.size()),
                () -> assertEquals("Test1", rules.get(0).getName())
        );
    }

    @Test
    void findByIdIn() {
        accidentRuleRepository.save(new Rule("Test1"));
        accidentRuleRepository.save(new Rule("Test2"));
        accidentRuleRepository.save(new Rule("Test3"));
        List<Rule> rules = accidentRuleRepository.findAll();
        List<Integer> ids = new ArrayList<>();
        for (Rule rule : rules) {
            ids.add(rule.getId());
        }
        Set<Rule> foundRules = accidentRuleRepository.findByIdIn(ids);
        assertThat(foundRules.size()).isEqualTo(3);
    }
}