package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Article;
import me.firstSpring.dto.AddArticleRequest;
import me.firstSpring.dto.ArticleResponse;
import me.firstSpring.dto.UpdateArticleRequest;
import me.firstSpring.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {
    private final BlogService blogService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request,
                                              Principal principal){
        Article savedArticle = blogService.save(request,principal.getName());

        //요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()//모든 게시물을 가져온 뒤 그 결과를 List<Article>형태로 반환한다.
                .stream()//List<Article> 형태를 스트림으로 변환한다.
                .map(ArticleResponse::new)//Article 객체를 새로운 ArticleResponse객채로 반환한다.
                .toList();//map메소드로 새로운 스트림이 생성되었으니 이를 다시 리스트로 반환해 List<ArticleReponse>타입의 articles를 생성한다.
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateAricle(@PathVariable long id,
                                                @RequestBody UpdateArticleRequest request){
        Article updateArticle = blogService.update(id,request);

        return ResponseEntity.ok().body(updateArticle);
    }
}
