package org.servidor.dao.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.servidor.dao.common.HibConstants;

@Entity
@Table(name = HibConstants.MESSAGE)
@ToString
@Getter
@Setter
@AllArgsConstructor
public class MessageHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name= HibConstants.ID_FOLDER, referencedColumnName = HibConstants.ID, nullable=false)
    @ToString.Exclude
    private FolderHibernate folder;

    public MessageHibernate() {

    }

    public MessageHibernate(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
