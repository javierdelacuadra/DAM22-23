package com.example.data;

import com.example.data.modelo.NewspaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface NewspapersRepository extends JpaRepository<NewspaperEntity, String> {

    @Transactional
    @Modifying
    @Query("update NewspaperEntity u set u.name = ?1, u.release_date = ?2 where u.id = ?3")
    Optional<NewspaperEntity> updateNewspaper(String name, String release_date, String id);
}
