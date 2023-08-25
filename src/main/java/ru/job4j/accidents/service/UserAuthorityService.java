package ru.job4j.accidents.service;

import ru.job4j.accidents.model.UserAuthority;

import java.util.Collection;
import java.util.Optional;

public interface UserAuthorityService {
    Optional<UserAuthority> save(UserAuthority userAuthority);
    boolean deleteById(Long id);
    Optional<UserAuthority> findById(Long id);
    Collection<UserAuthority> findAll();
    Optional<UserAuthority> findByAuthority(String authority);
}
