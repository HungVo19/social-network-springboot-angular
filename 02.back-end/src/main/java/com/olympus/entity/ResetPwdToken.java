package com.olympus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reset_password")
public class ResetPwdToken {
    @Id
    @GeneratedValue
    @Column(name = "reset_id")
    private Long id;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    public ResetPwdToken(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
