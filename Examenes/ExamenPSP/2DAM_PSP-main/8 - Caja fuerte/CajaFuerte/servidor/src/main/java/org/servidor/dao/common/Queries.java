package org.servidor.dao.common;

public class Queries {
    public static final String HQL_GET_FOLDER_BY_USER_AND_NAME = "HQL_GET_FOLDER_BY_USER_AND_NAME";
    public static final String HQL_GET_ALL_FOLDERS = "HQL_GET_ALL_FOLDERS";
    public static final String FROM_FOLDER_HIBERNATE_F_WHERE_F_USER_NAME_USER_NAME_AND_F_NAME_FOLDER_NAME = "FROM FolderHibernate f WHERE f.user.name = :userName AND f.name = :nameFolder";
    public static final String FROM_FOLDER_HIBERNATE_F_WHERE_F_USER_NAME_USER_NAME = "FROM FolderHibernate f where f.user.name = :userName";
    public static final String HQL_GET_USER_BY_NAME = "HQL_GET_USER_BY_NAME";
    public static final String FROM_USER_HIBERNATE_U_WHERE_U_NAME_USER_NAME = "FROM UserHibernate u WHERE u.name = :userName";

    private Queries() {
    }
}
