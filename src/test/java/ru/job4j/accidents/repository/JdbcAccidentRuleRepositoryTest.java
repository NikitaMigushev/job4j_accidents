package ru.job4j.accidents.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.accidents.configuration.JdbcConfig;
import ru.job4j.accidents.model.Rule;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcAccidentRuleRepositoryTest {
    private static JdbcAccidentRuleRepository jdbcRuleRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = JdbcAccidentRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var driver = properties.getProperty("jdbc.driver");
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new JdbcConfig();
        var datasource = configuration.ds(driver, url, username, password);
        var jdbc = configuration.jdbc(datasource);
        jdbcRuleRepository = new JdbcAccidentRuleRepository(jdbc);
    }

    @AfterEach
    public void clearRuleTable() {
        var rules = jdbcRuleRepository.findAll();
        for (Rule rule : rules) {
            jdbcRuleRepository.deleteById(rule.getId());
        }
    }

    @Test
    public void testSave() {
        var rule = new Rule(1, "Test type");
        var savedRule = jdbcRuleRepository.save(rule);
        var checkResult = jdbcRuleRepository.findById(savedRule.get().getId());
        assertThat(checkResult).isPresent();
        assertThat(checkResult.get().getName()).isEqualTo("Test type");
    }

    @Test
    public void testUpdate() {
        var rule = new Rule(1, "Test type");
        var savedRule = jdbcRuleRepository.save(rule);
        var updateRule = new Rule(savedRule.get().getId(), "Test type 2");
        jdbcRuleRepository.update(updateRule);
        var checkResult = jdbcRuleRepository.findById(updateRule.getId());
        assertThat(checkResult).isPresent();
        assertThat(checkResult.get().getName()).isEqualTo("Test type 2");
    }
}