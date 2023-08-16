package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcAccidentTypeRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<AccidentType> save(AccidentType accidentType) {
        String sql = "INSERT INTO accident_type (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, accidentType.getName());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            accidentType.setId(generatedId.intValue());
            return Optional.of(accidentType);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(AccidentType updatedAccidentType) {
        String updateSql = "UPDATE accident_type SET name = ? WHERE id = ?";
        int rowsAffected = jdbc.update(updateSql, updatedAccidentType.getName(), updatedAccidentType.getId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int id) {
        String deleteSql = "DELETE FROM accident_type WHERE id = ?";
        int rowsAffected = jdbc.update(deleteSql, id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        String sql = "SELECT id, name FROM accident_type WHERE id = ?";
        AccidentType accidentType = jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            int typeId = rs.getInt("id");
            String name = rs.getString("name");
            return new AccidentType(typeId, name);
        });
        return Optional.ofNullable(accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        String sql = "SELECT id, name FROM accident_type";
        List<AccidentType> accidentTypes = jdbc.query(sql, (rs, rowNum) -> {
            int typeId = rs.getInt("id");
            String name = rs.getString("name");
            return new AccidentType(typeId, name);
        });
        return accidentTypes;
    }
}
