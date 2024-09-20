package com.songsong.v3.user.entity;

import com.songsong.v3.playlist.entity.Playlist;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "user_register_date")
    private Date userRegisterDate;

    @Column(name = "user_like")
    private int userLike;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserCategory> userCategories = new ArrayList<>();

}
