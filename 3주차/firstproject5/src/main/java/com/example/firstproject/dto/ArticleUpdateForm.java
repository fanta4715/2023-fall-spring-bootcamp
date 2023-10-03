package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleUpdateForm {
    private Long id;
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id,title,content);
    }


}
