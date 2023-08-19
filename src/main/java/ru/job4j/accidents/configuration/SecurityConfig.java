package ru.job4j.accidents.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    DataSource ds;

    @Bean
    JdbcUserDetailsManager users(DataSource ds, PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(ds);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/login").permitAll()
                        .antMatchers("/**").hasAnyRole("ADMIN", "USER")
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
