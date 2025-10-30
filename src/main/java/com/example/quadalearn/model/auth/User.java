package com.example.quadalearn.model.auth;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String currentLevel; // vd: A2, B1
    private String goal;         // vd: B2, IELTS 6.5

    private String image;        // có thể null
    private String background;   // có thể null

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;       // có thể null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;     // có thể null

    public enum Gender {
        MALE, FEMALE
    }
}
