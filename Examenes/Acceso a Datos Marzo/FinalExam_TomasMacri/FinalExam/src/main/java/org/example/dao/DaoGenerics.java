package org.example.dao;

import org.bson.conversions.Bson;
import org.example.dao.impl.connections.DBConnection;
import org.example.dao.impl.connections.JPAUtil;

public interface DaoGenerics {
    int add(JPAUtil jpaUtil, Object o);

    int update(JPAUtil jpaUtil, Object o);

    int update(DBConnection dbConnection, Bson filter, Bson update);

    int delete(JPAUtil jpaUtil, Object o);
}
