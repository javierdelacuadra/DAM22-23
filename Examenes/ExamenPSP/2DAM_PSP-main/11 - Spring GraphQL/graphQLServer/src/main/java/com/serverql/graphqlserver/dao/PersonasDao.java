package com.serverql.graphqlserver.dao;

import com.serverql.graphqlserver.dao.common.DaoConstants;
import com.serverql.graphqlserver.dao.modelo.PersonaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PersonasDao extends JpaRepository<PersonaDTO, Integer> {

    @Transactional
    @Modifying
    @Query(DaoConstants.UPDATE_PERSONA)
    int updateById(String name, String surname, Integer id);

    @Query(DaoConstants.FIND_ALL_OF_PERSONA)
    Optional<PersonaDTO> findByIdComplete(Long id);

    @Override
    @Modifying
    @Query(DaoConstants.DELETE_PERSONA)
    void deleteById(Integer integer);

}