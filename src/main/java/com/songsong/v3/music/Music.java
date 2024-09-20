package com.songsong.v3.music;

import com.songsong.v3.user.entity.Category;
import com.songsong.v3.playlist.entity.Playlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "music")
@Setter
@Getter
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private int musicId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "music_name")
    private String musicName;
    @Column(name = "music_artist")
    private String musicArtist;
    @Column(name = "music_link")
    private String musicLink;

    @OneToMany(mappedBy = "music")
    private List<Playlist> playlists = new ArrayList<>();

}
