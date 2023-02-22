package com.basicrest.springrestbasic.domain.servicios.impl;

import com.basicrest.springrestbasic.dao.ArticlesDao;
import com.basicrest.springrestbasic.dao.modelo.ArticleDTO;
import com.basicrest.springrestbasic.dao.modelo.DataMapper;
import com.basicrest.springrestbasic.domain.excepciones.DataIntegrityException;
import com.basicrest.springrestbasic.domain.excepciones.NotFoundException;
import com.basicrest.springrestbasic.domain.modelo.Article;
import com.basicrest.springrestbasic.domain.servicios.ArticleServices;
import com.basicrest.springrestbasic.domain.servicios.common.ServicesConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServicesImpl implements ArticleServices {

    private final ArticlesDao articlesDao;

    private final DataMapper dataMapper;

    public ArticleServicesImpl(ArticlesDao articlesDao, DataMapper dataMapper) {
        this.articlesDao = articlesDao;
        this.dataMapper = dataMapper;
    }


    @Override
    @Transactional
    public List<Article> getAllArticles() {
        return articlesDao.findAll()
                .stream()
                .map(dataMapper::articleDTOToArticle)
                .toList();
    }

    @Override
    @Transactional
    public Article getArtById(Long id) {
        return articlesDao.findById(id)
                .map(dataMapper::articleDTOToArticle)
                .orElseThrow(() -> new NotFoundException(ServicesConstants.EL_ARTICULO_NO_EXISTE));

    }


    @Override
    @Transactional
    public Article createArticle(Article article) {
        //Para añadirlo
        try {
            ArticleDTO articleDTO = dataMapper.articleToArticleDTO(article);
            articleDTO.setId(0);
            articleDTO = articlesDao.save(articleDTO);
            article = dataMapper.articleDTOToArticle(articleDTO);
            return article;
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException(ServicesConstants.NO_SE_PUEDE_CREAR_EL_ARTICULO_PORQUE_EL_NEWSPAPER_NO_EXISTE);
        }

    }

    @Override
    public Article updateArticle(Article article) {

        int numeroFilasUpdate = articlesDao.updateById(article.getTitle(), article.getType(), article.getId());
        if (numeroFilasUpdate == 0) {
            throw new NotFoundException(ServicesConstants.EL_ARTICULO_NO_EXISTE);
        }
        return article;
    }


    @Override
    public void deleteById(Long id) {

        try {
            articlesDao.deleteById(id.intValue());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ServicesConstants.EL_ARTICULO_NO_EXISTE);
        }

    }
}
