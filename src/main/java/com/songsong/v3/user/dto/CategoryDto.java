package com.songsong.v3.user.dto;

import com.songsong.v3.user.entity.Category;
import lombok.*;

@Data
@NoArgsConstructor
public class CategoryDto {

    private int categoryId;
    private String categoryName;

//    public CategoryDto(String categoryName, int categoryId) {
//        this.categoryName = categoryName;
//        this.categoryId = categoryId;
//    }

    // Category 엔티티에서 DTO로 변환하는 생성자 추가
    public CategoryDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}
