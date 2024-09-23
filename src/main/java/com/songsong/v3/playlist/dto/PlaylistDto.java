package com.songsong.v3.playlist.dto;


import com.songsong.v3.music.dto.MusicDto;
import com.songsong.v3.playlist.entity.Playlist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaylistDto {

    private int userNo;
    private int musicId;
    private boolean sameUser;
    private MusicDto music;

    public PlaylistDto(int userNo, int musicId, boolean sameUser, MusicDto music) {
        this.userNo = userNo;
        this.musicId = musicId;
        this.sameUser = sameUser;
        this.music = music;
    }
    public PlaylistDto(int userNo, int musicId, boolean sameUser) {
        this.userNo = userNo;
        this.musicId = musicId;
        this.sameUser = sameUser;
    }

    public PlaylistDto(int musicId){
        this.musicId = musicId;
    }

    // Entity에서 DTO로 변환
//    public static PlaylistDto fromEntity(Playlist playlist) {
//        return new PlaylistDto(
//                playlist.getUser().getUserNo(),
//                playlist.getMusic().getMusicId(),
//                // SameUser
//                false
//        );
//    }
}
