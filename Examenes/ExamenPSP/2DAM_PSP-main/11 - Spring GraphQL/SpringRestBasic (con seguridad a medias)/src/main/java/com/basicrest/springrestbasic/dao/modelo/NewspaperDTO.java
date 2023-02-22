package com.basicrest.springrestbasic.dao.modelo;

import com.basicrest.springrestbasic.dao.common.DaoConstants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DaoConstants.NEWSPAPERS)
public class NewspaperDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DaoConstants.ID, nullable = false)
    private int id;

    @Column(name = DaoConstants.NAME, nullable = false)
    private String name;

    @Column(name = DaoConstants.RELEASE_DATE, nullable = false)
    private LocalDate releaseDate;

    @OneToMany(mappedBy = DaoConstants.NEWSPAPER)
    @ToString.Exclude
    private List<ArticleDTO> articles;
}
