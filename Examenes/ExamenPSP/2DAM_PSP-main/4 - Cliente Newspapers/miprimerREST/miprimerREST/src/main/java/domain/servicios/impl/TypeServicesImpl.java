package domain.servicios.impl;

import dao.impl.TypeDAOImpl;
import domain.modelo.TypeArt;
import domain.servicios.TypeServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class TypeServicesImpl implements TypeServices {

    private final TypeDAOImpl typeDAOImpl;

    @Inject
    public TypeServicesImpl(TypeDAOImpl typeDAOImpl) {
        this.typeDAOImpl = typeDAOImpl;
    }

    @Override
    public List<TypeArt> getAllTypes() {
        return typeDAOImpl.getAll();
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
