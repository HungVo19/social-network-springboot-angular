package com.olympus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "post_image")
@Data
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
