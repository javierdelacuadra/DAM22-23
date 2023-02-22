package domain.serivces.impl;

import dao.impl.TypeDAOImpl;
import domain.serivces.TypeServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.TypeArt;

import java.util.List;

public class TypeServicesImpl implements TypeServices {

    private final TypeDAOImpl typeDAOImpl;

    @Inject
    public TypeServicesImpl(TypeDAOImpl typeDAOImpl) {
        this.typeDAOImpl = typeDAOImpl;
    }

    @Override public Single<Either<String, List<TypeArt>>> getAllTypes(){
        return typeDAOImpl.getAll();
    }

}
