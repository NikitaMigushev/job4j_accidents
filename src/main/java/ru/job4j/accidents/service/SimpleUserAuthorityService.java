package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.UserAuthority;
import ru.job4j.accidents.repository.SpringDataUserAuthorityRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserAuthorityService implements UserAuthorityService {

    @Autowired
    private SpringDataUserAuthorityRepository authorityRepository;

    @Override
    public Optional<UserAuthority> save(UserAuthority userAuthority) {
        return Optional.ofNullable(authorityRepository.save(userAuthority));
    }

    @Override
    public boolean deleteById(Long id) {
        authorityRepository.deleteById(id);
        return !authorityRepository.existsById(id);
    }

    @Override
    public Optional<UserAuthority> findById(Long id) {
        return authorityRepository.findById(id);
    }

    @Override
    public Collection<UserAuthority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Optional<UserAuthority> findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }
}
