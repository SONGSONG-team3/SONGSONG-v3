package com.songsong.v3.like;

import com.songsong.v3.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int likeId;

    @ManyToOne
    @JoinColumn(name = "user_from", nullable = false)
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to", nullable = false)
    private User userTo;

    @Column(nullable = false)
    private int status;
}
