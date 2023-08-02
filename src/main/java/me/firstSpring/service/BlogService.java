package me.firstSpring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Article;
import me.firstSpring.dto.AddArticleRequest;
import me.firstSpring.dto.UpdateArticleRequest;
import me.firstSpring.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public Article save(AddArticleRequest request, String userName){ //블로그 글을 추가하는 메서드
        return blogRepository.save(request.toEntity(userName));
    } // 글 작성

    public List<Article> findAll(){
        return blogRepository.findAll();
    } //모든 글 목록 리스트로 조회

    public Article findById(long id) { //id로 글 조회
        return blogRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("not found: " + id));
                //예외 발생시 not found id 라는 메세지를 출력해줌
    }

    public void delete(long id){ //글 삭제
        Article article = blogRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("not found : " + id));
        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional //트랜젝션
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    private static void authorizeArticleAuthor(Article article){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }

}
