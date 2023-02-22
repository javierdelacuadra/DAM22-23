package org.servidor.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.servidor.dao.UserDao;
import org.servidor.dao.common.DaoConstants;
import org.servidor.dao.common.Queries;
import org.servidor.dao.impl.hibernate.JPAUtil;
import org.servidor.dao.modelo.UserHibernate;
import org.servidor.dao.modelo.datamapper.DataMapper;
import org.servidor.domain.errores.NotFoundException;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.User;

public class UserDaoImpl extends DaoGenericsImpl implements UserDao {

    JPAUtil jpaUtil;

    DataMapper dataMapper;

    @Inject
    public UserDaoImpl(JPAUtil jpaUtil, DataMapper dataMapper) {
        this.jpaUtil = jpaUtil;
        this.dataMapper = dataMapper;
    }

    @Override
    public User login(String username) {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            UserHibernate user = em.createNamedQuery(Queries.HQL_GET_USER_BY_NAME, UserHibernate.class)
                    .setParameter(AplicationConstants.USER_NAME, username).getSingleResult();
            return dataMapper.userHibToUser(user);
        }
    }

    @Override
    public String getCertificate(String username) {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            UserHibernate user = em.createNamedQuery(Queries.HQL_GET_USER_BY_NAME, UserHibernate.class)
                    .setParameter(AplicationConstants.USER_NAME, username).getSingleResult();
            return user.getCertificate();
        } catch (Exception e) {
            throw new NotFoundException(DaoConstants.USER + username + DaoConstants.NOT_FOUND);
        }
    }

    @Override
    public int add(User user) {
        return add(jpaUtil, dataMapper.userToUserHibernate(user), DaoConstants.ESE_NOMBRE_YA_ESTA_EN_USO_PRUEBA_OTRO);
    }

}
