package com.serverql.graphqlserver.dao;

import com.serverql.graphqlserver.dao.common.DaoConstants;
import com.serverql.graphqlserver.dao.modelo.LoginDTO;
import com.serverql.graphqlserver.dao.modelo.PersonaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginDao extends JpaRepository<PersonaDTO, Integer> {
    @Query(DaoConstants.SELECT_PERSONA_FROM_LOGIN_USERNAME)
    Optional<PersonaDTO> findByPersonaByUsername(String username);

    @Query(DaoConstants.SELECT_LOGIN_VALIDO)
    Optional<LoginDTO> findByLogin(String username);


    @Modifying
    @Query(DaoConstants.DELETE_LOGIN)
    void deleteByUsername(String integer);
}
