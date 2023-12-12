package com.olympus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "delete_status", nullable = false)
    private boolean deleteStatus;

    @Column(name = "privacy", nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "post")
    private List<PostImage> images;

    public Post() {
        this.deleteStatus = false;
        this.privacy = Privacy.PUBLIC;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }
}
