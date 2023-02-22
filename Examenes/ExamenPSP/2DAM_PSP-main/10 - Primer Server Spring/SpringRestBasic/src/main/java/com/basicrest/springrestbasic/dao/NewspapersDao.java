package com.basicrest.springrestbasic.dao;

import com.basicrest.springrestbasic.dao.common.DaoConstants;
import com.basicrest.springrestbasic.dao.modelo.NewspaperDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NewspapersDao extends JpaRepository<NewspaperDTO, Integer> {

    @Transactional
    @Modifying
    @Query(DaoConstants.UPDATE_NEWSPAPER)
    int updateById(String name, LocalDate releaseDate, Integer id);

    @Query(DaoConstants.GET_NEWSPAPER_WITH_ARTICLES_WHERE_ID)
    Optional<NewspaperDTO> findByIdComplete(Long id);

    @Override
    @Transactional
    @Modifying
    void deleteById(Integer integer);

}