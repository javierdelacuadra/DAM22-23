package com.serverql.graphqlserver.dao;

import com.serverql.graphqlserver.dao.common.DaoConstants;
import com.serverql.graphqlserver.dao.modelo.MascotaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotasDao extends JpaRepository<MascotaDTO, Integer> {

    @Transactional
    @Modifying
    @Query(DaoConstants.UPDATE_MASCOTA)
    int updateById(String name, String type, int age, Integer id);

    @Query(DaoConstants.SELECT_A_FROM_MASCOTA_DTO_A_WHERE_A_ID_1)
    Optional<MascotaDTO> findById(Long id);

    @Override
    @Transactional
    @Modifying
    void deleteById(Integer integer);

    @Query(DaoConstants.SELECT_A_FROM_MASCOTA_DTO_A_WHERE_A_PERSONA_ID_1)
    List<MascotaDTO> findByPersonaId(int id);
}
