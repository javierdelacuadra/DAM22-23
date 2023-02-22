package dao.impl;


import dao.TypeDAO;
import dao.retrofit.llamadas.NewspapersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.TypeArt;

import java.util.List;

public class TypeDAOImpl extends DaoGenericsImpl implements TypeDAO {


    private final NewspapersApi newspapersApi;

    @Inject
    public TypeDAOImpl(NewspapersApi newspapersApi) {
        this.newspapersApi = newspapersApi;
    }


    @Override
    public Single<Either<String, List<TypeArt>>> getAll() {
        return safeSingleApicall(newspapersApi.getAllTypesAsync());
    }

}
