package me.firstSpring.repository;

import java.util.Optional;
import me.firstSpring.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByTitle(String title);
}
