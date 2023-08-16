package ru.job4j.accidents.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccidentRowExtractor implements ResultSetExtractor<List<Accident>> {
    @Override
    public List<Accident> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Accident> accidentsMap = new HashMap<>();
        while (rs.next()) {
            int accidentId = rs.getInt("accident_id");
            Accident accident = accidentsMap.get(accidentId);

            if (accident == null) {
                accident = new Accident();
                accident.setId(accidentId);
                accident.setName(rs.getString("accident_name"));
                accident.setDescription(rs.getString("accident_description"));
                accident.setAddress(rs.getString("accident_address"));
                AccidentType type = new AccidentType();
                type.setId(rs.getInt("type_id"));
                type.setName(rs.getString("type_name"));
                accident.setType(type);
                accidentsMap.put(accidentId, accident);
            }
            int ruleId = rs.getInt("rule_id");
            if (ruleId != 0) {
                Rule rule = new Rule();
                rule.setId(ruleId);
                rule.setName(rs.getString("rule_name"));
                accident.getRules().add(rule);
            }
        }
        return new ArrayList<>(accidentsMap.values());
    }
}
