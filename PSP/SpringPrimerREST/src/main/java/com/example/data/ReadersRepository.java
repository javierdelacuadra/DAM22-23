package com.example.data;

import com.example.data.modelo.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface ReadersRepository extends JpaRepository<ReaderEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update ReaderEntity u set u.name = ?1, u.dateOfBirth = ?2 where u.id = ?3")
    int updateReader(String name, LocalDate dateOfBirth, String id);
}
