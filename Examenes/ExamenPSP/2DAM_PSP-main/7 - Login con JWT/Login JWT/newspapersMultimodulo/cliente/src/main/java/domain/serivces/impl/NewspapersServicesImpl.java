package domain.serivces.impl;

import dao.impl.NewspapersDAOImpl;
import domain.serivces.NewspapersServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Newspaper;
import retrofit2.Response;

import java.util.List;

public class NewspapersServicesImpl implements NewspapersServices {

    private final NewspapersDAOImpl newspapersDAOImpl;

    @Inject
    public NewspapersServicesImpl(NewspapersDAOImpl newspapersDAOImpl) {
        this.newspapersDAOImpl = newspapersDAOImpl;
    }

    @Override
    public Single<Either<String, List<Newspaper>>> getAll() {
        return newspapersDAOImpl.getAll();

    }

    @Override
    public Single<Either<String, Newspaper>> add(Newspaper newspaper) {
        return newspapersDAOImpl.add(newspaper);

    }

    @Override
    public Single<Either<String, Newspaper>> update(Newspaper news) {
        return newspapersDAOImpl.update(news);
    }

    @Override
    public Single<Either<String, Response<Object>>> delete(Newspaper newspaperSelected) {
        return newspapersDAOImpl.delete(newspaperSelected.getId());
    }

}
