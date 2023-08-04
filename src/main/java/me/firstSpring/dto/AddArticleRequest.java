package me.firstSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(String author, LocalDateTime createdAt){
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .createAt(createdAt)
                .build();
    }
}
