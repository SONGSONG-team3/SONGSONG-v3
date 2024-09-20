package com.songsong.v3.user.dto;

import com.songsong.v3.user.entity.Category;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    private String categoryName;

    public CategoryDto(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
}
    // Category 엔티티에서 DTO로 변환하는 생성자 추가
    public CategoryDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}