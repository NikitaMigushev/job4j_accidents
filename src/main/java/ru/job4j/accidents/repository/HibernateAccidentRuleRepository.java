package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class HibernateAccidentRuleRepository implements RuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Rule> save(Rule rule) {
        try {
            crudRepository.run(session -> session.save(rule));
            return Optional.of(rule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Rule updatedRule) {
        try {
            crudRepository.run(session -> session.merge(updatedRule));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Rule where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Rule> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT a FROM Rule a WHERE a.id = :fId",
                    Rule.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Rule> findAll() {
        try {
            return crudRepository.query(
                    "SELECT a FROM Rule a",
                    Rule.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Set<Rule> findByIds(String[] ids) {
        try {
            List<Integer> idList = Arrays.stream(ids)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            return new HashSet<>(crudRepository.query(
                    "SELECT r FROM Rule r WHERE r.id IN :idList",
                    Rule.class,
                    Map.of("idList", idList)
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptySet();

    }
}
