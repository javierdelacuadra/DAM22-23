package com.basicrest.springrestbasic.dao;

import com.basicrest.springrestbasic.dao.common.DaoConstants;
import com.basicrest.springrestbasic.dao.modelo.ArticleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ArticlesDao extends JpaRepository<ArticleDTO, Integer> {

    @Transactional
    @Modifying
    @Query(DaoConstants.UPDATE_ARTICLE)
    int updateById(String name, String type, Integer id);

    @Query(DaoConstants.SELECT_ARTICLE_WHERE_ID)
    Optional<ArticleDTO> findById(Long id);

    @Override
    @Transactional
    @Modifying
    void deleteById(Integer integer);

}
