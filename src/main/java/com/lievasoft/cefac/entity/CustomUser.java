package com.lievasoft.cefac.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class CustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "custom_user_sequence")
    @SequenceGenerator(name = "custom_user_sequence", sequenceName = "custom_user_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Collection<Token> tokens;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
