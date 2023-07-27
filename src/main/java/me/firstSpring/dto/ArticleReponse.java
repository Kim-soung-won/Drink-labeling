package me.firstSpring.dto;

import lombok.Getter;
import me.firstSpring.domain.Article;

@Getter
public class ArticleReponse {
    private final String title;
    private final String content;
    public ArticleReponse(Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
