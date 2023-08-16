package ru.job4j.accidents.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.accidents.configuration.JdbcConfig;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcAccidentRepositoryTest {
    private static JdbcAccidentRepository jdbcAccidentRepository;
    private static JdbcAccidentTypeRepository jdbcAccidentTypeRepository;
    private static JdbcAccidentRuleRepository jdbcAccidentRuleRepository;
    private static AccidentRowExtractor accidentRowExtractor;

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
        accidentRowExtractor = new AccidentRowExtractor();
        jdbcAccidentRepository = new JdbcAccidentRepository(jdbc, accidentRowExtractor);
        jdbcAccidentTypeRepository = new JdbcAccidentTypeRepository(jdbc);
        jdbcAccidentRuleRepository = new JdbcAccidentRuleRepository(jdbc);
    }

    @AfterEach
    public void clearAccidentTable() {
        var accidents = jdbcAccidentRepository.findAll();
        for (Accident accident : accidents) {
            jdbcAccidentRepository.deleteById(accident.getId());
        }
    }

    @BeforeEach
    public void clearTables() {
        var accidents = jdbcAccidentRepository.findAll();
        for (Accident accident : accidents) {
            jdbcAccidentRepository.deleteById(accident.getId());
        }
        for (AccidentType accidentType : jdbcAccidentTypeRepository.findAll()) {
            jdbcAccidentTypeRepository.deleteById(accidentType.getId());
        }

        for (Rule rule : jdbcAccidentRuleRepository.findAll()) {
            jdbcAccidentRuleRepository.deleteById(rule.getId());
        }
    }

    @Test
    public void testSave() {
        var savedAccidentType = jdbcAccidentTypeRepository.save(new AccidentType(1, "test"));
        var foundRules = jdbcAccidentRuleRepository.findAll();
        var rules = new HashSet<Rule>(foundRules);
        var savedAccident = jdbcAccidentRepository.save(new Accident(1, "Test", "Test", "Test", jdbcAccidentTypeRepository.findById(savedAccidentType.get().getId()).get(), rules));
        assertThat(savedAccident).isPresent();
    }

    @Test
    public void testUpdate() {
        var savedAccidentType = jdbcAccidentTypeRepository.save(new AccidentType(1, "test"));
        var foundRules = jdbcAccidentRuleRepository.findAll();
        var rules = new HashSet<Rule>(foundRules);
        var savedAccident = jdbcAccidentRepository.save(new Accident(1, "Test", "Test", "Test", jdbcAccidentTypeRepository.findById(savedAccidentType.get().getId()).get(), rules));
        var updatedAccident = new Accident(savedAccident.get().getId(), "Test 2", "Test", "Test", jdbcAccidentTypeRepository.findById(savedAccidentType.get().getId()).get(), rules);
        jdbcAccidentRepository.update(updatedAccident);
        assertThat(jdbcAccidentRepository.findById(updatedAccident.getId()).get().getName()).isEqualTo("Test 2");
    }
}