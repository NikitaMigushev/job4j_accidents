/*
package ru.job4j.accidents.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.accidents.configuration.JdbcConfig;
import ru.job4j.accidents.model.AccidentType;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcAccidentTypeRepositoryTest {
    private static JdbcAccidentTypeRepository jdbcAccidentTypeRepository;

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
        jdbcAccidentTypeRepository = new JdbcAccidentTypeRepository(jdbc);
    }

    @AfterEach
    public void clearAccidentTable() {
        var accidentTypes = jdbcAccidentTypeRepository.findAll();
        for (AccidentType accidentType : accidentTypes) {
            jdbcAccidentTypeRepository.deleteById(accidentType.getId());
        }
    }

    @Test
    public void testSave() {
        var accidentType = new AccidentType(1, "Test type");
        var savedAccidentType = jdbcAccidentTypeRepository.save(accidentType);
        var checkResult = jdbcAccidentTypeRepository.findById(savedAccidentType.get().getId());
        assertThat(checkResult).isPresent();
        assertThat(checkResult.get().getName()).isEqualTo("Test type");
    }

    @Test
    public void testUpdate() {
        var accidentType = new AccidentType(1, "Test type");
        var savedAccidentType = jdbcAccidentTypeRepository.save(accidentType);
        var updateAccidentType = new AccidentType(savedAccidentType.get().getId(), "Test type 2");
        jdbcAccidentTypeRepository.update(updateAccidentType);
        var checkResult = jdbcAccidentTypeRepository.findById(updateAccidentType.getId());
        assertThat(checkResult).isPresent();
        assertThat(checkResult.get().getName()).isEqualTo("Test type 2");
    }
}
*/