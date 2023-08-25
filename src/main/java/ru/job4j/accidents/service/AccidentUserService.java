package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentUser;

import java.util.Collection;
import java.util.Optional;

@Service
public interface AccidentUserService {
    Optional<AccidentUser> save(AccidentUser accidentUser);
    boolean update(AccidentUser updatedAccidentUser);
    boolean deleteByUsername(String username);
    Optional<AccidentUser> findByUsername(String username);
    Collection<AccidentUser> findAll();
    Optional<AccidentUser> findByUsernameAndPassword(String username, String password);
}
