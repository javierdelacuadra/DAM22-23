package com.basicrest.springrestbasic.dao.modelo;

import com.basicrest.springrestbasic.dao.common.DaoConstants;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DaoConstants.ARTICLES)
public class ArticleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DaoConstants.ID, nullable = false)
    private Integer id;

    @Column(name = DaoConstants.NAME, nullable = false)
    private String name;

    @Column(name = DaoConstants.TYPE, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = DaoConstants.ID_NEWS)
    @ToString.Exclude
    private NewspaperDTO newspaper;

    public ArticleDTO(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
