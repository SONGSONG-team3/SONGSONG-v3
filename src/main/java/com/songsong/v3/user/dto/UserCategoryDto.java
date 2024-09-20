package com.songsong.v3.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCategoryDto {
    private int userNo;      // Foreign key from user table
    private int categoryId;  // Foreign key from category table

}
