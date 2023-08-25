package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.UserAuthority;

import java.util.Optional;

public interface SpringDataUserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    Optional<UserAuthority> findByAuthority(String authority);
}
