package com.example.servidorgraphql.data;

import com.example.servidorgraphql.domain.modelo.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConductoresRepository extends JpaRepository<Conductor, Integer> {

    @Query("SELECT c FROM Conductor c WHERE c.camionID = ?1")
    Conductor findByCamion(Integer camionID);

}
