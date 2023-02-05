package servicios;

import data.DaoReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.ArticleType;
import model.Newspaper;
import model.Reader;

import java.util.Comparator;
import java.util.List;

public class ServicesReadersSQL {

    private final DaoReaders daoReaders;

    @Inject
    public ServicesReadersSQL(DaoReaders daoReaders) {
        this.daoReaders = daoReaders;
    }

    public int saveReader(Reader reader, Newspaper newspaper) {
        return daoReaders.add(reader, newspaper);
    }

    public Either<Integer, List<Reader>> getAllReaders() {
        Either<Integer, List<Reader>> readers = daoReaders.getAll();
        if (readers.isRight()) {
            List<Reader> list = readers.get();
            list.sort(Comparator.comparingInt(Reader::getId));
            return Either.right(list);
        } else {
            return Either.left(readers.getLeft());
        }
    }

    public int deleteReader(Reader reader, boolean delete) {
        if (!delete) {
            List<Reader> readers = daoReaders.getAll().get();
            Reader reader1 = null;
            if (reader.getName() != null) {
                reader1 = readers.stream().filter(r -> reader.getName().equals(r.getName())).findFirst().orElse(null);
            }
            if (reader1 != null) {
                if (reader1.getCancellationDate() == null) {
                    return -2;
                } else {
                    return daoReaders.delete(reader.getName());
                }
            }
        }
        return daoReaders.delete(reader.getName());
    }

    public Integer updateReader(Reader reader) {
        return daoReaders.update(reader);
    }

//    public Either<Integer, List<Reader>> getReadersByNewspaper(Newspaper newspaper) {
//        return daoReaders.getAll(newspaper);
//    }

    public Either<Integer, List<Reader>> getReadersByArticleType(ArticleType type) {
//        return daoReaders.getAll(type);
        return null;
    }

    public Reader getReadersById(Integer id) {
        return daoReaders.get(id);
    }

    public Either<Integer, List<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        return daoReaders.getAll(newspaper);
    }
}