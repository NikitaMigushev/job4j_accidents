package ru.job4j.accidents.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.SecurityUser;
import ru.job4j.accidents.repository.SpringDataAccidentUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final SpringDataAccidentUserRepository userRepository;

    public JpaUserDetailsService(SpringDataAccidentUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
