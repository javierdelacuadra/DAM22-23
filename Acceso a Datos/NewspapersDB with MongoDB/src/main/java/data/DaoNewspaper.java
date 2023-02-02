package data;

import io.vavr.control.Either;
import model.Newspaper;

import java.util.List;

public interface DaoNewspaper {
    Either<Integer, List<Newspaper>> getAll();

    Newspaper get(Newspaper newspaper);

    Integer add(Newspaper newspaper);

    Integer delete(Newspaper newspaper);

    Integer update(Newspaper newspaper);
}
