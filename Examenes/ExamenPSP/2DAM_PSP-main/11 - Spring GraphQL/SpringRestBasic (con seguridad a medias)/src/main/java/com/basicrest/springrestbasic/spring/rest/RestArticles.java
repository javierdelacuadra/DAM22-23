package com.basicrest.springrestbasic.spring.rest;

import com.basicrest.springrestbasic.domain.modelo.Article;
import com.basicrest.springrestbasic.domain.servicios.ArticleServices;
import com.basicrest.springrestbasic.spring.common.RestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestConstants.API_ARTICLES)
@Tag(name = RestConstants.ARTICLES_API, description = RestConstants.ARTICLES_ENDPOINT)
public class RestArticles {

    private final ArticleServices articleServices;

    public RestArticles(ArticleServices articleServices) {
        this.articleServices = articleServices;
    }

    @GetMapping
    @Operation(summary = RestConstants.GET_ALL_ARTICLES)
    public List<Article> getAllArticles() {
        return articleServices.getAllArticles();
    }

    @GetMapping(RestConstants.PATH_PARAM_ID)
    @Operation(summary = RestConstants.GET_ARTICLE_BY_ID)
    public Article getArticleById(@PathVariable Long id) {
        return articleServices.getArtById(id);
    }

    @Operation(summary = RestConstants.CREATE_AN_ARTICLE)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Article createArticle(@RequestBody Article article) {
        return articleServices.createArticle(article);
    }

    @Operation(summary = RestConstants.UPDATE_AN_ARTICLE)
    @PutMapping(RestConstants.PATH_PARAM_ID)
    public Article updateArticle(@RequestBody Article article) {
        return articleServices.updateArticle(article);
    }

    @Operation(summary = RestConstants.DELETE_AN_ARTICLE)
    @DeleteMapping(RestConstants.PATH_PARAM_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        articleServices.deleteById(id);
    }
}
