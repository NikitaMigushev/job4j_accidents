package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;
    private final AccidentRowExtractor accidentRowExtractor;

    @Override
    public Optional<Accident> save(Accident accident) {
        String sql = "insert into accident (name, description, address, accident_type_id) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getDescription());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            accident.setId(generatedId.intValue());
            saveAccidentRules(accident);
            return Optional.of(accident);
        }
        return Optional.empty();
    }

    private void saveAccidentRules(Accident accident) {
        for (Rule rule : accident.getRules()) {
            String sql = "insert into accident_rule_mapping (accident_id, rule_id) values (?, ?)";
            jdbc.update(sql, accident.getId(), rule.getId());
        }
    }

    @Override
    public boolean update(Accident accident) {
        String updateAccidentSql = "update accident set name = ?, description = ?, address = ?, accident_type_id = ? where id = ?";
        int rowsAffected = jdbc.update(
                updateAccidentSql,
                accident.getName(),
                accident.getDescription(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );

        if (rowsAffected > 0) {
            String deleteAccidentRulesSql = "delete from accident_rule_mapping where accident_id = ?";
            jdbc.update(deleteAccidentRulesSql, accident.getId());
            saveAccidentRules(accident);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        String deleteAccidentRulesSql = "delete from accident_rule_mapping where accident_id = ?";
        jdbc.update(deleteAccidentRulesSql, id);
        String deleteAccidentSql = "delete from accident where id = ?";
        int rowsAffected = jdbc.update(deleteAccidentSql, id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        String sql = "SELECT a.id AS accident_id, a.name AS accident_name, a.description AS accident_description, "
                + "a.address AS accident_address, t.id AS type_id, t.name AS type_name, "
                + "r.id AS rule_id, r.name AS rule_name "
                + "FROM accident a "
                + "INNER JOIN accident_type t ON a.accident_type_id = t.id "
                + "LEFT JOIN accident_rule_mapping arm ON a.id = arm.accident_id "
                + "LEFT JOIN accident_rule r ON arm.rule_id = r.id "
                + "WHERE a.id = ?";

        List<Accident> accidents = jdbc.query(sql, new Object[]{id}, accidentRowExtractor);

        // Check if the list contains any accidents, and return the first one if found
        if (!accidents.isEmpty()) {
            return Optional.of(accidents.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        String sql = "SELECT a.id AS accident_id, a.name AS accident_name, a.description AS accident_description, "
                + "a.address AS accident_address, t.id AS type_id, t.name AS type_name, "
                + "r.id AS rule_id, r.name AS rule_name "
                + "FROM accident a "
                + "INNER JOIN accident_type t ON a.accident_type_id = t.id "
                + "LEFT JOIN accident_rule_mapping arm ON a.id = arm.accident_id "
                + "LEFT JOIN accident_rule r ON arm.rule_id = r.id";

        return jdbc.query(sql, accidentRowExtractor);
    }

    @Override
    public Collection<Accident> findByAccidentType(AccidentType accidentType) {
        String sql = "SELECT a.id AS accident_id, a.name AS accident_name, a.description AS accident_description, "
                + "a.address AS accident_address, t.id AS type_id, t.name AS type_name, "
                + "r.id AS rule_id, r.name AS rule_name "
                + "FROM accident a "
                + "INNER JOIN accident_type t ON a.accident_type_id = t.id "
                + "LEFT JOIN accident_rule_mapping arm ON a.id = arm.accident_id "
                + "LEFT JOIN accident_rule r ON arm.rule_id = r.id "
                + "WHERE t.id = ?";

        return jdbc.query(sql, new Object[]{accidentType.getId()}, accidentRowExtractor);
    }
}
