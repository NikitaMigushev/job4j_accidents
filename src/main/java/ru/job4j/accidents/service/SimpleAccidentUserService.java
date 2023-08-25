package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.AccidentUser;
import ru.job4j.accidents.model.UserAuthority;
import ru.job4j.accidents.repository.SpringDataAccidentUserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SimpleAccidentUserService implements AccidentUserService {
    private final SpringDataAccidentUserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AccidentUser> save(AccidentUser accidentUser) {
        var authority = userAuthorityService.findByAuthority("ROLE_USER");
        var userAuthorities = new HashSet<UserAuthority>();
        userAuthorities.add(authority.get());
        accidentUser.setAuthorities(userAuthorities);
        accidentUser.setEnabled(true);
        accidentUser.setPassword(passwordEncoder.encode(accidentUser.getPassword()));
        var savedUser = userRepository.save(accidentUser);
        return Optional.ofNullable(userRepository.save(accidentUser));
    }

    @Override
    public boolean update(AccidentUser updatedAccidentUser) {
        Optional<AccidentUser> existingUserOptional = userRepository.findByUsername(updatedAccidentUser.getUsername());

        if (existingUserOptional.isPresent()) {
            AccidentUser existingUser = existingUserOptional.get();
            existingUser.setPassword(updatedAccidentUser.getPassword());
            existingUser.setEnabled(updatedAccidentUser.isEnabled());

            userRepository.save(existingUser);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        return true;
    }

    @Override
    public Optional<AccidentUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Collection<AccidentUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AccidentUser> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
