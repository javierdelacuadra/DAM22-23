package com.basicrest.springrestbasic.domain.servicios;

import com.basicrest.springrestbasic.domain.modelo.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleServices {
    @Transactional
    List<Article> getAllArticles();

    @Transactional
    Article getArtById(Long id);

    @Transactional
    Article createArticle(Article article);

    Article updateArticle(Article article);

    void deleteById(Long id);
}
