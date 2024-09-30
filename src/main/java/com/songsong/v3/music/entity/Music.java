package com.songsong.v3.music.entity;

import com.songsong.v3.user.entity.Category;
import com.songsong.v3.playlist.entity.Playlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "music")

public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private int musicId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "music_name")
    private String musicName;
    @Column(name = "music_artist")
    private String musicArtist;
    @Column(name = "music_link")
    private String musicLink;
    @Column(name = "music_language")
    private String musicLanguage;
    @Column(name = "music_genre")
    private String musicGenre;
    @Column(name = "music_country")
    private String musicCountry;


    @OneToMany(mappedBy = "music")
    private List<Playlist> playlists = new ArrayList<>();

}
