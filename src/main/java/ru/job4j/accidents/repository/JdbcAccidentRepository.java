package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;

    public JdbcAccidentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

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
        } else {
            return Optional.empty();
        }
    }

    private void saveAccidentRules(Accident accident) {
        for (Rule rule : accident.getRules()) {
            String sql = "insert into accident_rule_mapping (accident_id, rule_id) values (?, ?)";
            jdbc.update(sql, accident.getId(), rule.getId());
        }
    }

    @Override
    public boolean update(Accident accident) {
        String sql = "update accident set name = ? where id = ?";
        return jdbc.update(sql, accident.getName(), accident.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "delete from accident where id = ?";
        return jdbc.update(sql, id) > 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        String sql = "select * from accident where id = ?";
        Accident accident = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Accident.class), id);
        return Optional.ofNullable(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        String sql = "select * from accident";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Accident.class));
    }

    @Override
    public Collection<Accident> findByAccidentType(AccidentType accidentType) {
        throw new UnsupportedOperationException("findByAccidentType is not applicable for this table structure");
    }
}
