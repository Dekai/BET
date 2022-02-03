package com.dk.alirr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedEntityGraph(
        name = "graph.user-with-cars-and-drivers",
        attributeNodes = {
                @NamedAttributeNode(value = "authorities"),
                @NamedAttributeNode(value = "cars", subgraph = "graph.car-with-drivers")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "graph.car-with-drivers",
                        attributeNodes = {
                                @NamedAttributeNode("drivers")
                        }
                )
        }
)
@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String perm;
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    private Set<Authority> authorities = new HashSet<>();

}
