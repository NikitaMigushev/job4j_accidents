/*
package ru.job4j.accidents.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateAccidentRepositoryTest {
    private static Session session;
    private static SessionFactory sf;
    private static AccidentRepository accidentRepository;

    private static AccidentTypeRepository accidentTypeRepository;

    private static RuleRepository ruleRepository;

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
        accidentRepository = new HibernateAccidentRepository(new CrudRepository(sf));
        accidentTypeRepository = new HibernateAccidentTypeRepository(new CrudRepository(sf));
        ruleRepository = new HibernateAccidentRuleRepository(new CrudRepository(sf));

        accidentTypeRepository.save(new AccidentType(1, "Test 1"));
        ruleRepository.save(new Rule(1, "Test 2"));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Accident").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @AfterAll
    public static void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM AccidentType").executeUpdate();
        session.getTransaction().commit();
        session.close();

        Session session2 = sf.openSession();

        session2.beginTransaction();
        session2.createQuery("DELETE FROM Rule").executeUpdate();
        session2.getTransaction().commit();
        session2.close();
    }

    @Test
    public void testSave() {
        var rules = new HashSet<Rule>(ruleRepository.findAll());
        var accident = new Accident(1, "Test", "Test", "Test", accidentTypeRepository.findById(1).get(), rules);
        var savedAccident = accidentRepository.save(accident);
        System.out.println("check here");
        assertThat(savedAccident).isPresent();
    }
}
*/