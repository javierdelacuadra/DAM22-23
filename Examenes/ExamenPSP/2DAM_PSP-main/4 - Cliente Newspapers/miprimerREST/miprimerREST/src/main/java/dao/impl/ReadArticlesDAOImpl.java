package dao.impl;

import dao.ReadArticlesDAO;
import dao.common.DAOConstants;
import dao.common.DBConstants;
import dao.common.SQLQueries;
import domain.modelo.QueryBadRatedArticles;
import domain.modelo.ReadArticle;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.NotFoundException;
import domain.modelo.errores.OtherErrorException;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;
import java.util.Objects;


public class ReadArticlesDAOImpl implements ReadArticlesDAO {


    DBConnection dbConnection;

    @Inject
    public ReadArticlesDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ReadArticle> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            return jtm.query(SQLQueries.SELECT_FROM_READARTICLES, BeanPropertyRowMapper.newInstance(ReadArticle.class));
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public Either<Integer, ReadArticle> get(int id) {
        return null;
    }

    @Override
    public int update(ReadArticle readArticle) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            // QUERY EXTRA PARA DEVOLVER EL ID DEL READ ART QUE ESTOY ACTUALIZANDO
            int id = getIDReadArt(readArticle);
            readArticle.setId(id);
            int code;
            code = jtm.update(SQLQueries.UPDATE_READARTICLES_SET_RATING_WHERE_ID_READER_AND_ID_ARTICLE, readArticle.getRating(), readArticle.getId_reader(), readArticle.getId_article());
            if (code > 0) {
                return code;
            }
            throw new NotFoundException(DAOConstants.EL_ID_DEL_READER_Y_O_DEL_ARTICULO_NO_SON_VALIDOS);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    private int getIDReadArt(ReadArticle readArticle) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_ID_FROM_READARTICLES_WHERE_ID_READER_AND_ID_ARTICLE)) {
            preparedStatement.setInt(1, readArticle.getId_reader());
            preparedStatement.setInt(2, readArticle.getId_article());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new NotFoundException(DAOConstants.THE_READER + readArticle.getId_reader() + DAOConstants.HAS_NOT_READ_THE_ARTICLE + readArticle.getId_article());

        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (SQLException e) {
            throw new BaseDeDatosException(e.getMessage());
        }
    }

    @Override
    public int add(ReadArticle ra) {
        int code = -2;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ADD_READ_ARTICLE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ra.getId_reader());
            preparedStatement.setInt(2, ra.getId_article());
            preparedStatement.setInt(3, ra.getRating());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                ra.setId(rs.getInt(1));
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                // SI EL READER YA RATEO EL ARTICULO, SE SACA OTRO ERROR
                throw new InvalidFieldsException(DAOConstants.THE_READER + ra.getId_reader() + DAOConstants.HAS_ALREADY_READ_THE_ARTICLE + ra.getId_article());
            } else if (e.getErrorCode() == 1452) {
                throw new NotFoundException(DAOConstants.EL_ID_DEL_READER_Y_O_DEL_ARTICULO_NO_SON_VALIDOS);
            }
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
        return code;
    }

    // POR EL DAO PATTERN, LUCIA PIDIO QUE ESTEN TODAS LAS FUNCIONES AUNQUE NO HAYAN SIDO IMPLEMENTADAS
    @Override
    public int delete(int id) {
        return -1;
    }

    @Override
    public List<QueryBadRatedArticles> getAllDifficultSpringQuery(String idNewspaper) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            List<QueryBadRatedArticles> badRatedArticles = jtm.query(SQLQueries.IMPOSSIBLE_QUERY,
                    (rs, rowNum) -> {
                        int badRatings = rs.getInt(DBConstants.BAD_RATINGS);
                        boolean badRater = badRatings > 4;
                        return new QueryBadRatedArticles(rs.getString(DBConstants.NAME_ARTICLE), badRater);
                    }, idNewspaper
            );
            if (!badRatedArticles.isEmpty()) {
                return badRatedArticles;
            }
            throw new NotFoundException(DAOConstants.THE_NEWSPAPER + idNewspaper + DAOConstants.HAS_NO_BAD_RATED_ARTICLES);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NestedRuntimeException e) {
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }

    @Override
    public int getRatingOfReadArticleByReader(String idReader, String idArticle) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getHikariDataSource());
            int mayBeNullRating = Objects.requireNonNullElse(jtm.queryForObject(SQLQueries.SELECT_RATING_FROM_READARTICLES, Integer.class, idReader, idArticle), -1);
            if (mayBeNullRating > 0) {
                return mayBeNullRating;
            }
            throw new NotFoundException(DAOConstants.THE_USER + idReader + DAOConstants.HAS_NOT_RATED_THE_ARTICLE + idArticle);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NestedRuntimeException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.equals(DAOConstants.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0)) {
                throw new NotFoundException(DAOConstants.THE_USER + idReader + DAOConstants.HAS_NOT_RATED_THE_ARTICLE + idArticle);
            }
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            throw new OtherErrorException(e.getMessage());
        }
    }
}
