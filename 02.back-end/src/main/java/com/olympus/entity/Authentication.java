package com.olympus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authentication")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Authentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long id;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "code", nullable = false)
    private String code;

    public Authentication(User user, String code) {
        this.user = user;
        this.code = code;
    }
}
