package org.servidor.dao;


import org.servidor.dao.impl.hibernate.JPAUtil;

public interface DaoGenerics {

    int add(JPAUtil jpaUtil, Object o, String msjError);

    int update(JPAUtil jpaUtil, Object o);

    int delete(JPAUtil jpaUtil, Object o);
}
