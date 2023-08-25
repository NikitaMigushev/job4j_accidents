/*
package ru.job4j.accidents.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateAccidentTypeRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static AccidentTypeRepository accidentTypeRepository;

    @BeforeAll
    public static void setup() throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sf = configuration.buildSessionFactory(serviceRegistry);
        sf = configuration.buildSessionFactory();
        session = sf.openSession();
        accidentTypeRepository = new HibernateAccidentTypeRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        session.beginTransaction();
        session.createQuery("DELETE FROM AccidentType").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    void save() {
        AccidentType accidentType = new AccidentType();
        Optional<AccidentType> savedAccidentType = accidentTypeRepository.save(accidentType);
        assertThat(savedAccidentType).isPresent();
        assertThat(savedAccidentType.get().getId()).isPositive();
    }

    @Test
    void update() {
        AccidentType accidentType = new AccidentType();
        accidentTypeRepository.save(accidentType);
        boolean isUpdated = accidentTypeRepository.update(accidentType);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        AccidentType accidentType = new AccidentType();
        accidentTypeRepository.save(accidentType);
        boolean isDeleted = accidentTypeRepository.deleteById(accidentType.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        AccidentType accidentType = new AccidentType();
        var savedAccidentType = accidentTypeRepository.save(accidentType);
        Optional<AccidentType> foundAccidentType = accidentTypeRepository.findById(savedAccidentType.get().getId());
        assertThat(foundAccidentType).isPresent();
        assertThat(foundAccidentType.get()).isEqualTo(accidentType);
    }

    @Test
    void findAll() {
        AccidentType accidentType1 = new AccidentType();
        accidentTypeRepository.save(accidentType1);
        AccidentType accidentType2 = new AccidentType();
        accidentTypeRepository.save(accidentType2);
        Collection<AccidentType> accidentTypes = accidentTypeRepository.findAll();
        assertThat(accidentTypes).hasSize(2);
    }
}
*/