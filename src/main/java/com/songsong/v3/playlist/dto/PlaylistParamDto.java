package com.songsong.v3.playlist.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaylistParamDto {
    private int limit;
    private int offset;
    private int searchCategory;
    private int userNo; // 현재 사용자 userNo

    public PlaylistParamDto(int limit, int offset, int searchCategory, int userNo) {
        this.limit = limit;
        this.offset = offset;
        this.searchCategory = searchCategory;
        this.userNo = userNo;
    }
}