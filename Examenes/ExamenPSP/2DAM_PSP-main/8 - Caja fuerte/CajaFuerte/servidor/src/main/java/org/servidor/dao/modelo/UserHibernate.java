package org.servidor.dao.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.servidor.dao.common.HibConstants;
import org.servidor.dao.common.Queries;

@Entity
@Table(name = HibConstants.USER)
@Getter
@Setter
@AllArgsConstructor
@NamedQuery(name = Queries.HQL_GET_USER_BY_NAME, query = Queries.FROM_USER_HIBERNATE_U_WHERE_U_NAME_USER_NAME)
public class UserHibernate {

    @Id
    private String name;

    @Column
    private String password;

    @Column
    private String role;


    public UserHibernate() {
        // hibernate needs this
    }
}
