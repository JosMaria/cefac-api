package com.lievasoft.cefac.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @UuidGenerator
    @Column(length = 100)
    private String id;

    @Column(unique = true, nullable = false, length = 300)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public CustomUser user;

    public enum TokenType {
        BEARER
    }
}
