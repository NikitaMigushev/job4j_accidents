package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accident")
public class Accident {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String address;
    @ManyToOne
    @JoinColumn(name = "accident_type_id")
    private AccidentType type;
    @ManyToMany
    @JoinTable(
            name = "ACCIDENT_RULE_MAPPING",
            joinColumns = { @JoinColumn(name = "accident_id") },
            inverseJoinColumns = { @JoinColumn(name = "rule_id") }
    )
    @Fetch(FetchMode.JOIN)
    private Set<Rule> rules = new HashSet<>();;

    public Accident(String name, String text, String address) {
        this.name = name;
        this.description = text;
        this.address = address;
    }
}