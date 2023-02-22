package com.example.servidorgraphql.data;

import com.example.servidorgraphql.domain.modelo.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CamionesRepository extends JpaRepository<Camion, Integer> {
    @Query("SELECT c FROM Camion c WHERE c.empresaID = ?1")
    List<Camion> findByEmpresaId(int id);
}
