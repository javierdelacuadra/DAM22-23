package com.serverql.graphqlserver.dao.common;

public class DaoConstants {

    public static final String AGE = "age";
    public static final String SURNAME = "surname";
    public static final String PERSONA = "persona";
    public static final String SELECT_A_FROM_MASCOTA_DTO_A_WHERE_A_PERSONA_ID_1 = "SELECT a FROM MascotaDTO a WHERE a.persona.id = ?1";

    // get a persona from login.username
    public static final String SELECT_P_FROM_PERSONA_DTO_P_WHERE_P_LOGIN_USERNAME_1 = "SELECT p FROM PersonaDTO p WHERE p.login.username = ?1";
    public static final String SELECT_LOGIN_VALIDO = "SELECT l FROM LoginDTO l WHERE l.username = ?1";
    public static final String DELETE_LOGIN = "delete from LoginDTO l where l.username = ?1";
    public static final String DELETE_PERSONA = "delete from PersonaDTO p where p.id = ?1";

    private DaoConstants() {
    }
    public static final String MASCOTAS = "mascotas";
    public static final String TYPE = "type";
    public static final String ID_PERSONA = "id_persona";
    public static final String PERSONAS = "personas";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String UPDATE_MASCOTA = "update MascotaDTO n set n.name = ?1, n.type = ?2, n.age= ?3 where n.id = ?4";
    public static final String SELECT_A_FROM_MASCOTA_DTO_A_WHERE_A_ID_1 = "SELECT a FROM MascotaDTO a WHERE a.id = ?1";
    public static final String UPDATE_PERSONA = "update PersonaDTO n set n.name = ?1, n.surname = ?2 where n.id = ?3";
    public static final String FIND_ALL_OF_PERSONA = "SELECT n FROM PersonaDTO n LEFT JOIN n.mascotas WHERE n.id = ?1";

    public static final String  SELECT_PERSONA_FROM_LOGIN_USERNAME = "SELECT p FROM PersonaDTO p WHERE p.login.username = ?1";
}
