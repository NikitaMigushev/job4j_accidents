package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@AllArgsConstructor
public class JdbcAccidentRuleRepository implements RuleRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<Rule> save(Rule rule) {
        String sql = "INSERT INTO accident_rule (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, rule.getName());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            rule.setId(generatedId.intValue());
            return Optional.of(rule);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Rule updatedRule) {
        String updateSql = "UPDATE accident_rule SET name = ? WHERE id = ?";
        int rowsAffected = jdbc.update(updateSql, updatedRule.getName(), updatedRule.getId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int id) {
        String deleteSql = "DELETE FROM accident_rule WHERE id = ?";
        int rowsAffected = jdbc.update(deleteSql, id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Rule> findById(int id) {
        String sql = "SELECT id, name FROM accident_rule WHERE id = ?";
        Rule rule = jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            int typeId = rs.getInt("id");
            String name = rs.getString("name");
            return new Rule(typeId, name);
        });
        return Optional.ofNullable(rule);
    }

    @Override
    public Collection<Rule> findAll() {
        String sql = "SELECT id, name FROM accident_rule";
        List<Rule> rules = jdbc.query(sql, (rs, rowNum) -> {
            int typeId = rs.getInt("id");
            String name = rs.getString("name");
            return new Rule(typeId, name);
        });
        return rules;
    }

    @Override
    public Set<Rule> findByIds(String[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptySet();
        }
        Set<Rule> ruleSet = new HashSet<>();
        for (String id : ids) {
            findById(Integer.parseInt(id)).ifPresent(ruleSet::add);
        }
        return ruleSet;
    }
}
