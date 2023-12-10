package com.olympus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "gender")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_id", nullable = false, unique = true)
    private Long id;
    @Column(name = "gender")
    private String gender;
}
