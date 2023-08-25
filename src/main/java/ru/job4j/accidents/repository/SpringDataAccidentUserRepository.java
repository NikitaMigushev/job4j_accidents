package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.accidents.model.AccidentUser;

import java.util.Optional;


public interface SpringDataAccidentUserRepository extends JpaRepository<AccidentUser, Long> {
    @Modifying
    @Query("DELETE FROM AccidentUser u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);
    Optional<AccidentUser> findByUsername(String username);
    Optional<AccidentUser> findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM AccidentUser u JOIN FETCH u.authorities WHERE u.id = :userId")
    Optional<AccidentUser> findByIdWithAuthorities(@Param("userId") Long userId);
}
