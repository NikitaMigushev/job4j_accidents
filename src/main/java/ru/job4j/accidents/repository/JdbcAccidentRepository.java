package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

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
        String sql = "insert into accidents (name) values (?)";
        jdbc.update(sql, accident.getName());
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        String sql = "update accidents set name = ? where id = ?";
        return jdbc.update(sql, accident.getName(), accident.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "delete from accidents where id = ?";
        return jdbc.update(sql, id) > 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        String sql = "select * from accidents where id = ?";
        Accident accident = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Accident.class), id);
        return Optional.ofNullable(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        String sql = "select * from accidents";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Accident.class));
    }

    @Override
    public Collection<Accident> findByAccidentType(AccidentType accidentType) {
        throw new UnsupportedOperationException("findByAccidentType is not applicable for this table structure");
    }
}
