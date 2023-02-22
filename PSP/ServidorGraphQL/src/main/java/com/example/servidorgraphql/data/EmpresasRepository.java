package com.example.servidorgraphql.data;

import com.example.servidorgraphql.domain.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresasRepository extends JpaRepository<Empresa, Integer> {
}
