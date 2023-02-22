package ui.pantallas.paisdetalle;

import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import dominio.serivcios.impl.ServiciosPaisesImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class PaisDetalleViewModel {

    ServiciosPaisesImpl serviciosPaisesImpl;

    @Inject
    public PaisDetalleViewModel(ServiciosPaisesImpl serviciosPaisesImpl) {
        this.serviciosPaisesImpl = serviciosPaisesImpl;
        state = new SimpleObjectProperty<>(new PaisDetalleState(null, null, null));
    }

    private final ObjectProperty<PaisDetalleState> state;

    public ReadOnlyObjectProperty<PaisDetalleState> getState() {
        return state;
    }

    public void loadPaises() {
        Either<String, List<Pais>> either = serviciosPaisesImpl.getTodosLosPaises();
        PaisDetalleState pd;
        if (either.isRight()) {
            pd = new PaisDetalleState(null, either.get(), null);
        } else {
            pd = new PaisDetalleState(either.getLeft(), null, null);
        }
        state.setValue(pd);
    }

    public void getPaisDetalle(String iso) {
        Either<String, PaisDetalle> paisDetallado = serviciosPaisesImpl.getPaisDetalle(iso);
        PaisDetalleState pd;
        if (paisDetallado.isRight()) {
            pd = new PaisDetalleState(null, state.getValue().getPaisesList(), paisDetallado.get());
        } else {
            pd = new PaisDetalleState(paisDetallado.getLeft(), null, null);
        }
        state.setValue(pd);
    }

}
