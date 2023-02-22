package domain.servicios.impl;

import dao.impl.TypeDAOImpl;
import domain.servicios.TypeServices;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.TypeArt;

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
}
