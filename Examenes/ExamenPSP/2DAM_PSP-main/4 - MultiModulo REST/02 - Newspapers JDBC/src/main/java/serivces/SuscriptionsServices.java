package serivces;

import io.vavr.control.Either;

public interface SuscriptionsServices {

    Either<String, String> changeSuscription(int idReader, int idNews, boolean isAlreadySuscribed);

}
