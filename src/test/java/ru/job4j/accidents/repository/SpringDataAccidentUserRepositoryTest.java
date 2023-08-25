package ru.job4j.accidents.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.AccidentUser;
import ru.job4j.accidents.model.UserAuthority;
import ru.job4j.accidents.service.SimpleAccidentUserService;
import ru.job4j.accidents.service.UserAuthorityService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SpringDataAccidentUserRepositoryTest {
    @Autowired
    private SpringDataAccidentUserRepository userRepository;

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Autowired
    SimpleAccidentUserService userService;

    private Set<UserAuthority> userAuthorities = new HashSet<>();

    @BeforeEach
    public void beforeEach() {
        var savedUserAuthority = userAuthorityService.save(new UserAuthority("ROLE_USER"));
        userAuthorities.add(savedUserAuthority.get());
    }

    @RepeatedTest(2)
    void saveRetrieve() {
        var savedUser = userService.save(new AccidentUser("Test1", "123", userAuthorities));
        List<AccidentUser> users = userRepository.findAll();
        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("Test1", users.get(0).getUsername())
        );
    }

    @RepeatedTest(2)
    void findByNameTest() {
        userRepository.save(new AccidentUser("Test1", "123", userAuthorities));
        var foundUser = userRepository.findByUsername("Test1");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPassword()).isEqualTo("123");
    }

    @RepeatedTest(2)
    void deleteByIdTest() {
        AccidentUser user = userRepository.save(new AccidentUser("Test1", "123", userAuthorities));
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @RepeatedTest(2)
    void deleteByUserNameTest() {
        AccidentUser user = userRepository.save(new AccidentUser("Test333", "123", userAuthorities));
        userRepository.deleteByUsername(user.getUsername());
        assertThat(userRepository.findByUsername(user.getUsername())).isEmpty();
    }

    @Test
    void findByUserNameAndPassowordTest() {
        userRepository.save(new AccidentUser("Test1", "123", userAuthorities));
        userRepository.save(new AccidentUser("Test2", "321", userAuthorities));
        var foundUser = userRepository.findByUsernameAndPassword("Test2", "321");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("Test2");
    }
}