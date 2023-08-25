package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "accident_user")
@AllArgsConstructor
@NoArgsConstructor
public class AccidentUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String username;
    private String password;
    private boolean enabled;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<UserAuthority> authorities = new HashSet<>();

    public AccidentUser(String username, String password, Set<UserAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
