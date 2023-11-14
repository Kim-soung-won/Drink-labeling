package me.firstSpring.controller.Blog;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Article;
import me.firstSpring.dto.Article.ArticleListViewResponse;
import me.firstSpring.dto.Article.ArticleViewResponse;
import me.firstSpring.service.BlogService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){ //Model : HTML쪽으로 값을 넘겨주는 객체
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles",articles);

        return "articleList";
    }
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article",new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    //id 키를 가진 쿼리 파라미터의 값을 id변수에 매핑
    public String newArticle(@RequestParam(required = false) Long id, Model model){
        if(id == null){ //id가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else{ //있으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
