package domain.servicios.impl;

import dao.impl.ReadArticlesDAOImpl;
import domain.modelo.QueryBadRatedArticles;
import domain.modelo.ReadArticle;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.InvalidFieldsException;
import domain.servicios.ReadArticleServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;

import java.util.List;

public class ReadArticleServicesImpl implements ReadArticleServices {

    ReadArticlesDAOImpl readArticlesDAO;

    @Inject
    public ReadArticleServicesImpl(ReadArticlesDAOImpl readArticlesDAO) {
        this.readArticlesDAO = readArticlesDAO;
    }

    @Override
    public ReadArticle add(ReadArticle ra) {
        if (ra.getRating() < 1 || ra.getRating() > 5) {
            throw new InvalidFieldsException(ServicesConstants.EL_RATING_DEL_ARTICULO_DEBE_SER_DE_1_A_5);
        }
        int code = readArticlesDAO.add(ra);

        if (code > 0) {
            return ra;
        }
        throw new BaseDeDatosException(ServicesConstants.NO_SE_HA_PODIDO_ANADIR_EL_NUEVO_ARTICULO_LEIDO);
    }

    @Override
    public ReadArticle update(ReadArticle ra) {
        if (ra.getRating() < 1 || ra.getRating() > 5) {
            throw new InvalidFieldsException(ServicesConstants.EL_RATING_DEL_ARTICULO_DEBE_SER_DE_1_A_5);
        }
        if (readArticlesDAO.update(ra) == 1) {
            return ra;
        }
        throw new BaseDeDatosException(ServicesConstants.NO_SE_HA_PODIDO_ACTUALIZAR_EL_ARTICULO_LEIDO);
    }

    @Override
    public List<QueryBadRatedArticles> getAllDifficultSpringQuery(String idNewspaper) {
        return readArticlesDAO.getAllDifficultSpringQuery(idNewspaper);
    }

    @Override
    public int getRating(String idReader, String idArticle) {
        return readArticlesDAO.getRatingOfReadArticleByReader(idReader, idArticle);
    }

    @Override
    public List<ReadArticle> getAll() {
        return readArticlesDAO.getAll();
    }
}
