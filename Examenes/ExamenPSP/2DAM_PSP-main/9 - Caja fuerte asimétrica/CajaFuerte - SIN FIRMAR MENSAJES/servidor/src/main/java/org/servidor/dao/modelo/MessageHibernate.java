package org.servidor.dao.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.servidor.dao.common.HibConstants;
import org.servidor.dao.common.Queries;

@Entity
@Table(name = HibConstants.MESSAGE)
@ToString
@Getter
@Setter
@AllArgsConstructor
@NamedQuery(name = Queries.HQL_GET_MESSAGES_BY_FOLDER_ID, query = Queries.FROM_MESSAGE_HIBERNATE_M_WHERE_M_FOLDER_ID_FOLDER_ID)
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
