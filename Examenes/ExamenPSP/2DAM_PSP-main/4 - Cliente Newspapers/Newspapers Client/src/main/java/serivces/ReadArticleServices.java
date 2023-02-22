package serivces;

import io.vavr.control.Either;
import model.ReadArticle;

public interface ReadArticleServices {

    Either<String, String> add(ReadArticle ra);
}
