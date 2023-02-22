package org.servidor.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.servidor.dao.UserDao;
import org.servidor.dao.common.Queries;
import org.servidor.dao.impl.hibernate.JPAUtil;
import org.servidor.dao.modelo.UserHibernate;
import org.servidor.dao.modelo.datamapper.DataMapper;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.User;

public class UserDaoImpl extends DaoGenericsImpl implements UserDao {

    Pbkdf2PasswordHash passwordHash;
    JPAUtil jpaUtil;

    DataMapper dataMapper;

    @Inject
    public UserDaoImpl(Pbkdf2PasswordHash passwordHash, JPAUtil jpaUtil, DataMapper dataMapper) {
        this.passwordHash = passwordHash;
        this.jpaUtil = jpaUtil;
        this.dataMapper = dataMapper;
    }

    @Override
    public User login(String username, String password) {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            UserHibernate user = em.createNamedQuery(Queries.HQL_GET_USER_BY_NAME, UserHibernate.class)
                    .setParameter(AplicationConstants.USER_NAME, username).getSingleResult();
            if (user != null && passwordHash.verify(password.toCharArray(), user.getPassword())) {
                return dataMapper.userHibToUser(user);
            }
        }
        return null;
    }

    @Override
    public int add(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        return add(jpaUtil, dataMapper.userToUserHibernate(user));
    }

}
