package serivces.impl;

import dao.impl.TypeDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.TypeArt;
import serivces.TypeServices;
import serivces.common.ServicesConstants;

import java.util.List;

public class TypeServicesImpl implements TypeServices {

    private TypeDAOImpl typeDAOImpl;

    @Inject
    public TypeServicesImpl(TypeDAOImpl typeDAOImpl) {
        this.typeDAOImpl = typeDAOImpl;
    }

    @Override public Either<String, List<TypeArt>> getAllTypes(){
        Either<Integer, List<TypeArt>> either = typeDAOImpl.getAll();
        if (either.isLeft()) {
            return switch (either.getLeft()) {
                case -2 ->
                        Either.left(ServicesConstants.ERROR_GETTING_ALL_TYPES + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case -3 -> Either.left(ServicesConstants.ERROR_GETTING_ALL_TYPES);
                default ->
                        Either.left(ServicesConstants.ERROR_GETTING_ALL_TYPES + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            };
        } else if (either.get().isEmpty()) {
            return Either.left(ServicesConstants.THERE_ARE_NO_TYPES_IN_THE_DB);
        } else {
            return Either.right(either.get());
        }

    }

    @Override
    public Either<String, TypeArt> get(TypeArt typeArt) {
        return typeDAOImpl.get(typeArt);
    }

    @Override
    public int add(TypeArt typeArt) {
        return typeDAOImpl.add(typeArt);
    }

    @Override
    public int update(TypeArt typeArt) {
        return typeDAOImpl.update(typeArt);
    }

    @Override
    public int delete(TypeArt typeArt) {
        return typeDAOImpl.delete(typeArt);
    }
}
