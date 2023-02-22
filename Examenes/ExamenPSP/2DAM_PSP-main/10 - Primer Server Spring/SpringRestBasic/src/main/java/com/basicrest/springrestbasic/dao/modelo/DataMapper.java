package com.basicrest.springrestbasic.dao.modelo;

import com.basicrest.springrestbasic.domain.modelo.Article;
import com.basicrest.springrestbasic.domain.modelo.Newspaper;
import org.springframework.stereotype.Component;

@Component
public class DataMapper {

    public NewspaperDTO newspaperToNewspaperDTO(Newspaper newspaper) {
        return new NewspaperDTO(newspaper.getId(), newspaper.getName(), newspaper.getReleaseDate(), newspaper.getArticles().stream().map(this::articleToArticleDTO).toList());
    }

    public Newspaper newspaperDTOToNewspaper(NewspaperDTO newspaperDTO) {
        return new Newspaper(newspaperDTO.getId(), newspaperDTO.getName(), newspaperDTO.getReleaseDate(), newspaperDTO.getArticles().stream().map(this::articleDTOToArticle).toList());
    }

    public ArticleDTO articleToArticleDTO(Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getType());
    }

    public Article articleDTOToArticle(ArticleDTO articleDTO) {
        return new Article(articleDTO.getId(), articleDTO.getName(), articleDTO.getType(), articleDTO.getNewspaper().getId());
    }


}
