package com.example.firstproject.entity;

import com.example.firstproject.dto.ArticleUpdateForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public void update(ArticleUpdateForm dto) {
        this.id=dto.getId();
        this.title=dto.getTitle();
        this.content=dto.getContent();
    }
}
