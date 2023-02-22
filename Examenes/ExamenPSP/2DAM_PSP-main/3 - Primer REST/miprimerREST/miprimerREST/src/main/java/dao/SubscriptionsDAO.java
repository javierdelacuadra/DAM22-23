package dao;

import domain.modelo.Suscription;
import io.vavr.control.Either;

import java.util.List;

public interface SubscriptionsDAO {
    Either<Integer, List<Suscription>> getAll(int id);

    Either<Integer, Suscription> get(int id);

    int delete(int id);

    int update(Suscription suscription);

    int add(Suscription suscription);
}
