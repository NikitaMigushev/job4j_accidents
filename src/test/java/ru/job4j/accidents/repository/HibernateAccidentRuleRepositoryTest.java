package ru.job4j.accidents.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateAccidentRuleRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

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
        ruleRepository = new HibernateAccidentRuleRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        session.beginTransaction();
        session.createQuery("DELETE FROM Rule").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    void save() {
        Rule rule = new Rule();
        Optional<Rule> savedRule = ruleRepository.save(rule);
        assertThat(savedRule).isPresent();
        assertThat(savedRule.get().getId()).isPositive();
    }

    @Test
    void update() {
        Rule rule = new Rule();
        ruleRepository.save(rule);
        boolean isUpdated = ruleRepository.update(rule);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Rule rule = new Rule();
        ruleRepository.save(rule);
        boolean isDeleted = ruleRepository.deleteById(rule.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Rule rule = new Rule();
        var savedRule = ruleRepository.save(rule);
        Optional<Rule> foundRule = ruleRepository.findById(savedRule.get().getId());
        assertThat(foundRule).isPresent();
        assertThat(foundRule.get()).isEqualTo(rule);
    }

    @Test
    void findAll() {
        Rule rule1 = new Rule();
        ruleRepository.save(rule1);
        Rule rule2 = new Rule();
        ruleRepository.save(rule2);
        Collection<Rule> rules = ruleRepository.findAll();
        assertThat(rules).hasSize(2);
    }
}