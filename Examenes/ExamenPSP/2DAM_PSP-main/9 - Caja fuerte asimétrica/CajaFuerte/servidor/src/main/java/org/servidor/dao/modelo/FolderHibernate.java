package org.servidor.dao.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.servidor.dao.common.HibConstants;
import org.servidor.dao.common.Queries;

import java.util.List;

@Entity
@Table(name = HibConstants.FOLDER)
@ToString
@Getter
@Setter
@NamedQuery(name = Queries.HQL_GET_FOLDER_BY_USER_AND_NAME, query = Queries.FROM_FOLDER_HIBERNATE_F_WHERE_F_USER_NAME_USER_NAME_AND_F_NAME_FOLDER_NAME)
@NamedQuery(name = Queries.HQL_GET_ALL_FOLDERS, query = Queries.FROM_FOLDER_HIBERNATE_F_WHERE_F_USER_NAME_USER_NAME)
public class FolderHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String password;

    @Column(name = HibConstants.READ_ONLY)
    private boolean readOnly;

    @Column(name = HibConstants.IS_OWNER)
    private boolean isOwner;

    @Column(name = HibConstants.ORIGINAL_FOLDER_ID)
    private Integer originalFolderId;

    @ManyToOne
    @JoinColumn(name= HibConstants.NAME_USER, referencedColumnName = HibConstants.NAME, nullable=false)
    private UserHibernate user;


    @OneToMany(mappedBy = HibConstants.FOLDER)
    @ToString.Exclude
    private List<MessageHibernate> messages;

    public FolderHibernate() {
        // hibernate needs this
    }
    public FolderHibernate(int id, String name, String password, boolean readOnly, boolean isOwner, Integer originalFolderId, List<MessageHibernate> toList) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.readOnly = readOnly;
        this.isOwner = isOwner;
        this.messages = toList;
        this.originalFolderId = originalFolderId;
    }

    public FolderHibernate(int id, String name, String password, boolean readOnly, boolean isOwner, Integer originalFolderId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.readOnly = readOnly;
        this.isOwner = isOwner;
        this.originalFolderId = originalFolderId;
    }
}
