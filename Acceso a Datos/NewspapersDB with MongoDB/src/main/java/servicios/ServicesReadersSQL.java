package servicios;

import data.DaoReaders;
import data.DaoSubscriptions;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.ArticleType;
import model.Newspaper;
import model.Reader;

import java.util.List;

public class ServicesReadersSQL {

    private final DaoReaders daoReaders;
    private final DaoSubscriptions daoSubscriptions;

    @Inject
    public ServicesReadersSQL(DaoReaders daoReaders, DaoSubscriptions daoSubscriptions) {
        this.daoReaders = daoReaders;
        this.daoSubscriptions = daoSubscriptions;
    }

    public int saveReader(Reader reader) {
        return daoReaders.save(reader);
    }

    public Either<Integer, List<Reader>> getAllReaders() {
        return daoReaders.getAll();
    }

    public int deleteReader(Reader reader, boolean delete) {
        if (!delete) {
            if (!daoSubscriptions.get(reader).get().isEmpty()) {
                return -2;
            } else {
                return daoReaders.delete(reader);
            }
        } else {
            return daoReaders.delete(reader);
        }
    }

    public Integer updateReader(Reader reader) {
        return daoReaders.update(reader);
    }

    public Either<Integer, List<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        return daoReaders.getAll(newspaper);
    }

    public Either<Integer, List<Reader>> getReadersByArticleType(ArticleType type) {
        return daoReaders.getAll(type);
    }

    public Reader getReadersById(int id) {
        return daoReaders.get(id);
    }
}