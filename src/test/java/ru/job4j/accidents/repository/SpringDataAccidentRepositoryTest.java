package ru.job4j.accidents.repository;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SpringDataAccidentRepositoryTest {
    @Autowired
    private SpringDataAccidentRepository accidentRepository;

    @Autowired
    private SpringDataAccidentTypeRepository accidentTypeRepository;

    @RepeatedTest(2)
    void saveRetrieve() {
        accidentRepository.save(new Accident("Test"));
        List<Accident> accidents = accidentRepository.findAll();

        assertAll(
                () -> assertEquals(1, accidents.size()),
                () -> assertEquals("Test", accidents.get(0).getName())
        );
    }

    @Test
    void findByTypeTest() {
        accidentTypeRepository.save(new AccidentType("Type1"));
        accidentTypeRepository.save(new AccidentType("Type2"));
        List<AccidentType> accidentTypes = accidentTypeRepository.findAll();
        accidentRepository.save(new Accident("Test", accidentTypes.get(0)));
        accidentRepository.save(new Accident("Test2", accidentTypes.get(1)));
        Collection<Accident> accidents = accidentRepository.findByType(accidentTypes.get(0));
        assertThat(accidents.size()).isEqualTo(1);
        assertThat(accidents.iterator().next().getName()).isEqualTo("Test");
        assertThat(accidents.iterator().next().getType().getName()).isEqualTo("Type1");
    }
}